package com.jafp.bankapi.transaction.service;

import com.jafp.bankapi.account.entity.Account;
import com.jafp.bankapi.account.entity.AccountStatus;
import com.jafp.bankapi.account.repository.AccountRepository;
import com.jafp.bankapi.common.exception.InvalidOperationException;
import com.jafp.bankapi.common.exception.ResourceNotFoundException;
import com.jafp.bankapi.common.generator.TransferReferenceGenerator;
import com.jafp.bankapi.transaction.dto.DepositRequestDTO;
import com.jafp.bankapi.transaction.dto.TransactionResponseDTO;
import com.jafp.bankapi.transaction.dto.TransferRequestDTO;
import com.jafp.bankapi.transaction.dto.WithdrawalRequestDTO;
import com.jafp.bankapi.transaction.entity.Transaction;
import com.jafp.bankapi.transaction.entity.TransactionStatus;
import com.jafp.bankapi.transaction.entity.TransactionType;
import com.jafp.bankapi.transaction.mapper.TransactionMapper;
import com.jafp.bankapi.transaction.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements  TransactionService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;
  private final TransferReferenceGenerator transferReferenceGenerator;

  public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionMapper transactionMapper, TransferReferenceGenerator transferReferenceGenerator) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
    this.transactionMapper = transactionMapper;
    this.transferReferenceGenerator = transferReferenceGenerator;
  }

  @Override
  @Transactional
  public TransactionResponseDTO deposit(DepositRequestDTO requestDTO) {

    Account account = accountRepository.findById(requestDTO.accountId())
            .orElseThrow(() ->
                    new ResourceNotFoundException("Account not found")
            );

    if (account.getStatus() != AccountStatus.ACTIVE) {
      throw new InvalidOperationException("Account is not active");
    }

    BigDecimal balanceBefore = account.getBalance();

    account.deposit(requestDTO.amount());

    BigDecimal balanceAfter = account.getBalance();

    accountRepository.save(account);

    Transaction transaction = new Transaction();

    transaction.setAccount(account);
    transaction.setTransactionType(TransactionType.DEPOSIT);
    transaction.setAmount(requestDTO.amount());
    transaction.setBalanceBefore(balanceBefore);
    transaction.setBalanceAfter(balanceAfter);
    transaction.setDescription(requestDTO.description());
    transaction.setStatus(TransactionStatus.COMPLETED);

    Transaction savedTransaction = transactionRepository.save(transaction);

    return transactionMapper.toResponseDTO(savedTransaction);
  }

  @Override
  @Transactional
  public TransactionResponseDTO withdraw(
          WithdrawalRequestDTO requestDTO
  ) {

    Account account = accountRepository.findById(requestDTO.accountId())
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Account not found"
                    )
            );
    if(account.getStatus() != AccountStatus.ACTIVE){
      throw new InvalidOperationException(
              "Account is not active"
      );
    }

    if(!account.hasAvailableBalance(requestDTO.amount())){
      throw new InvalidOperationException(
              "Insufficient balance"
      );
    }

    BigDecimal balanceBefore = account.getBalance();
    account.withdraw(requestDTO.amount());
    BigDecimal balanceAfter = account.getBalance();
    accountRepository.save(account);
    Transaction transaction = new Transaction();
    transaction.setAccount(account);
    transaction.setTransactionType(TransactionType.WITHDRAWAL);
    transaction.setAmount(requestDTO.amount());
    transaction.setBalanceBefore(balanceBefore);
    transaction.setBalanceAfter(balanceAfter);
    transaction.setDescription(requestDTO.description());
    transaction.setStatus(
            TransactionStatus.COMPLETED
    );
    Transaction savedTransaction =
            transactionRepository.save(transaction);
    return transactionMapper.toResponseDTO(savedTransaction);
  }


  @Override
  @Transactional
  public TransactionResponseDTO transfer(
          TransferRequestDTO requestDTO
  ) {

    if(requestDTO.sourceAccountId()
            .equals(requestDTO.destinationAccountId())){

      throw new InvalidOperationException(
              "Source and destination account cannot be the same"
      );
    }


    Account sourceAccount =
            accountRepository.findById(
                            requestDTO.sourceAccountId()
                    )
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Source account not found"
                            )
                    );


    Account destinationAccount =
            accountRepository.findById(
                            requestDTO.destinationAccountId()
                    )
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Destination account not found"
                            )
                    );


    if(sourceAccount.getStatus() != AccountStatus.ACTIVE){

      throw new InvalidOperationException(
              "Source account is not active"
      );
    }


    if(destinationAccount.getStatus() != AccountStatus.ACTIVE){

      throw new InvalidOperationException(
              "Destination account is not active"
      );
    }


    if(!sourceAccount.hasAvailableBalance(
            requestDTO.amount()
    )){

      throw new InvalidOperationException(
              "Insufficient balance"
      );
    }


    BigDecimal sourceBalanceBefore =
            sourceAccount.getBalance();


    BigDecimal destinationBalanceBefore =
            destinationAccount.getBalance();



    sourceAccount.withdraw(
            requestDTO.amount()
    );


    destinationAccount.deposit(
            requestDTO.amount()
    );


    BigDecimal sourceBalanceAfter =
            sourceAccount.getBalance();


    BigDecimal destinationBalanceAfter =
            destinationAccount.getBalance();



    accountRepository.save(sourceAccount);

    accountRepository.save(destinationAccount);



    String transferReference =
            transferReferenceGenerator.generate();



    Transaction transferOut =
            new Transaction();


    transferOut.setAccount(sourceAccount);

    transferOut.setTransactionType(
            TransactionType.TRANSFER_OUT
    );

    transferOut.setAmount(
            requestDTO.amount()
    );

    transferOut.setBalanceBefore(
            sourceBalanceBefore
    );

    transferOut.setBalanceAfter(
            sourceBalanceAfter
    );

    transferOut.setTransferReference(
            transferReference
    );

    transferOut.setDescription(
            requestDTO.description()
    );

    transferOut.setStatus(
            TransactionStatus.COMPLETED
    );



    Transaction transferIn =
            new Transaction();


    transferIn.setAccount(destinationAccount);

    transferIn.setTransactionType(
            TransactionType.TRANSFER_IN
    );

    transferIn.setAmount(
            requestDTO.amount()
    );

    transferIn.setBalanceBefore(
            destinationBalanceBefore
    );

    transferIn.setBalanceAfter(
            destinationBalanceAfter
    );

    transferIn.setTransferReference(
            transferReference
    );

    transferIn.setDescription(
            requestDTO.description()
    );

    transferIn.setStatus(
            TransactionStatus.COMPLETED
    );



    transactionRepository.saveAll(
            List.of(
                    transferOut,
                    transferIn
            )
    );



    return transactionMapper.toResponseDTO(
            transferOut
    );
  }

  @Override
  public List<TransactionResponseDTO> findByAccount(Long accountId) {

    accountRepository.findById(accountId)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Account not found"
                    )
            );

    return transactionRepository
            .findByAccountIdOrderByCreatedAtDesc(accountId)
            .stream()
            .map(transactionMapper::toResponseDTO)
            .toList();
  }
}
