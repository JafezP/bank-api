package com.jafp.bankapi.transaction.service;

import com.jafp.bankapi.account.entity.Account;
import com.jafp.bankapi.account.entity.AccountStatus;
import com.jafp.bankapi.common.exception.InvalidOperationException;
import com.jafp.bankapi.common.exception.ResourceNotFoundException;
import com.jafp.bankapi.transaction.dto.DepositRequestDTO;
import com.jafp.bankapi.transaction.dto.TransactionResponseDTO;
import com.jafp.bankapi.transaction.dto.TransferRequestDTO;
import com.jafp.bankapi.transaction.dto.WithdrawalRequestDTO;
import com.jafp.bankapi.transaction.entity.Transaction;
import com.jafp.bankapi.account.repository.AccountRepository;
import com.jafp.bankapi.transaction.mapper.TransactionMapper;
import com.jafp.bankapi.transaction.repository.TransactionRepository;
import com.jafp.bankapi.common.generator.TransferReferenceGenerator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {


  @Mock
  private AccountRepository accountRepository;


  @Mock
  private TransactionRepository transactionRepository;


  @Mock
  private TransactionMapper transactionMapper;


  @Mock
  private TransferReferenceGenerator transferReferenceGenerator;


  @InjectMocks
  private TransactionServiceImpl transactionService;

  @Test
  void deposit_ShouldCreateTransaction_WhenAccountIsActive(){

    DepositRequestDTO requestDTO =
            new DepositRequestDTO(
                    1L,
                    new BigDecimal("500"),
                    "Initial deposit"
            );


    Account account = new Account();

    account.setId(1L);
    account.setStatus(AccountStatus.ACTIVE);
    account.setBalance(new BigDecimal("1000"));



    when(accountRepository.findById(1L))
            .thenReturn(Optional.of(account));



    when(accountRepository.save(any(Account.class)))
            .thenReturn(account);



    Transaction transaction = new Transaction();

    transaction.setId(1L);



    when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(transaction);



    TransactionResponseDTO responseDTO =
            new TransactionResponseDTO(
                    1L,
                    "123456789012",
                    "DEPOSIT",
                    null,
                    new BigDecimal("500"),
                    new BigDecimal("1000"),
                    new BigDecimal("1500"),
                    "Initial deposit",
                    "COMPLETED",
                    null
            );



    when(transactionMapper.toResponseDTO(transaction))
            .thenReturn(responseDTO);



    TransactionResponseDTO response =
            transactionService.deposit(requestDTO);



    assertEquals(
            "DEPOSIT",
            response.transactionType()
    );

    assertEquals(
            new BigDecimal("500"),
            response.amount()
    );

  }

  @Test
  void deposit_ShouldThrowException_WhenAccountDoesNotExist(){

    DepositRequestDTO requestDTO =
            new DepositRequestDTO(
                    1L,
                    new BigDecimal("500"),
                    "Initial deposit"
            );


    when(accountRepository.findById(1L))
            .thenReturn(Optional.empty());



    assertThrows(
            ResourceNotFoundException.class,
            () -> transactionService.deposit(requestDTO)
    );



    verify(accountRepository, never())
            .save(any(Account.class));


    verify(transactionRepository, never())
            .save(any(Transaction.class));

  }

  @Test
  void deposit_ShouldThrowException_WhenAccountIsInactive(){

    DepositRequestDTO requestDTO =
            new DepositRequestDTO(
                    1L,
                    new BigDecimal("500"),
                    "Initial deposit"
            );


    Account account = new Account();

    account.setId(1L);
    account.setStatus(AccountStatus.CLOSED);
    account.setBalance(new BigDecimal("1000"));



    when(accountRepository.findById(1L))
            .thenReturn(Optional.of(account));



    assertThrows(
            InvalidOperationException.class,
            () -> transactionService.deposit(requestDTO)
    );



    verify(accountRepository, never())
            .save(any(Account.class));


    verify(transactionRepository, never())
            .save(any(Transaction.class));

  }

  @Test
  void withdraw_ShouldCreateTransaction_WhenAccountHasBalance(){

    WithdrawalRequestDTO requestDTO =
            new WithdrawalRequestDTO(
                    1L,
                    new BigDecimal("500"),
                    "Cash withdrawal"
            );


    Account account = new Account();

    account.setId(1L);
    account.setStatus(AccountStatus.ACTIVE);
    account.setBalance(new BigDecimal("1000"));



    when(accountRepository.findById(1L))
            .thenReturn(Optional.of(account));



    when(accountRepository.save(any(Account.class)))
            .thenReturn(account);



    Transaction transaction = new Transaction();

    transaction.setId(1L);



    when(transactionRepository.save(any(Transaction.class)))
            .thenReturn(transaction);



    TransactionResponseDTO responseDTO =
            new TransactionResponseDTO(
                    1L,
                    "123456789012",
                    "WITHDRAWAL",
                    null,
                    new BigDecimal("500"),
                    new BigDecimal("1000"),
                    new BigDecimal("500"),
                    "Cash withdrawal",
                    "COMPLETED",
                    null
            );



    when(transactionMapper.toResponseDTO(transaction))
            .thenReturn(responseDTO);



    TransactionResponseDTO response =
            transactionService.withdraw(requestDTO);



    assertEquals(
            "WITHDRAWAL",
            response.transactionType()
    );


    assertEquals(
            new BigDecimal("500"),
            response.amount()
    );

  }

  @Test
  void withdraw_ShouldThrowException_WhenAccountDoesNotExist(){

    WithdrawalRequestDTO requestDTO =
            new WithdrawalRequestDTO(
                    1L,
                    new BigDecimal("500"),
                    "Cash withdrawal"
            );


    when(accountRepository.findById(1L))
            .thenReturn(Optional.empty());



    assertThrows(
            ResourceNotFoundException.class,
            () -> transactionService.withdraw(requestDTO)
    );



    verify(accountRepository, never())
            .save(any(Account.class));


    verify(transactionRepository, never())
            .save(any(Transaction.class));

  }

  @Test
  void withdraw_ShouldThrowException_WhenAccountIsInactive(){

    WithdrawalRequestDTO requestDTO =
            new WithdrawalRequestDTO(
                    1L,
                    new BigDecimal("500"),
                    "Cash withdrawal"
            );


    Account account = new Account();

    account.setId(1L);
    account.setStatus(AccountStatus.CLOSED);
    account.setBalance(new BigDecimal("1000"));



    when(accountRepository.findById(1L))
            .thenReturn(Optional.of(account));



    assertThrows(
            InvalidOperationException.class,
            () -> transactionService.withdraw(requestDTO)
    );



    verify(accountRepository, never())
            .save(any(Account.class));


    verify(transactionRepository, never())
            .save(any(Transaction.class));

  }

  @Test
  void withdraw_ShouldThrowException_WhenBalanceIsInsufficient(){

    WithdrawalRequestDTO requestDTO =
            new WithdrawalRequestDTO(
                    1L,
                    new BigDecimal("1000"),
                    "Cash withdrawal"
            );


    Account account = new Account();

    account.setId(1L);
    account.setStatus(AccountStatus.ACTIVE);
    account.setBalance(new BigDecimal("500"));



    when(accountRepository.findById(1L))
            .thenReturn(Optional.of(account));



    assertThrows(
            InvalidOperationException.class,
            () -> transactionService.withdraw(requestDTO)
    );



    verify(accountRepository, never())
            .save(any(Account.class));


    verify(transactionRepository, never())
            .save(any(Transaction.class));

  }

  @Test
  void transfer_ShouldCreateTransactions_WhenAccountsAreValid(){

    TransferRequestDTO requestDTO =
            new TransferRequestDTO(
                    1L,
                    2L,
                    new BigDecimal("300"),
                    "Payment"
            );


    Account sourceAccount = new Account();

    sourceAccount.setId(1L);
    sourceAccount.setStatus(AccountStatus.ACTIVE);
    sourceAccount.setBalance(new BigDecimal("1000"));



    Account destinationAccount = new Account();

    destinationAccount.setId(2L);
    destinationAccount.setStatus(AccountStatus.ACTIVE);
    destinationAccount.setBalance(new BigDecimal("500"));



    when(accountRepository.findById(1L))
            .thenReturn(Optional.of(sourceAccount));


    when(accountRepository.findById(2L))
            .thenReturn(Optional.of(destinationAccount));



    when(accountRepository.save(any(Account.class)))
            .thenAnswer(
                    invocation -> invocation.getArgument(0)
            );



    when(transferReferenceGenerator.generate())
            .thenReturn("TRF-123456");



    Transaction transaction = new Transaction();

    transaction.setId(1L);



    when(transactionRepository.saveAll(anyList()))
            .thenReturn(List.of(transaction, transaction));



    TransactionResponseDTO responseDTO =
            new TransactionResponseDTO(
                    1L,
                    "123456789012",
                    "TRANSFER_OUT",
                    null,
                    new BigDecimal("300"),
                    new BigDecimal("1000"),
                    new BigDecimal("700"),
                    "Payment",
                    "COMPLETED",
                    LocalDateTime.now()
            );



    when(transactionMapper.toResponseDTO(any(Transaction.class)))
            .thenReturn(responseDTO);



    TransactionResponseDTO response =
            transactionService.transfer(requestDTO);



    assertEquals(
            "TRANSFER_OUT",
            response.transactionType()
    );


    assertEquals(
            new BigDecimal("300"),
            response.amount()
    );



    verify(accountRepository, times(2))
            .save(any(Account.class));


    verify(transactionRepository)
            .saveAll(anyList());

  }

  @Test
  void transfer_ShouldThrowException_WhenAccountsAreTheSame(){

    TransferRequestDTO requestDTO =
            new TransferRequestDTO(
                    1L,
                    1L,
                    new BigDecimal("300"),
                    "Payment"
            );


    assertThrows(
            InvalidOperationException.class,
            () -> transactionService.transfer(requestDTO)
    );


    verify(accountRepository, never())
            .save(any(Account.class));


    verify(transactionRepository, never())
            .saveAll(anyList());

  }

  @Test
  void transfer_ShouldThrowException_WhenSourceAccountDoesNotExist(){

    TransferRequestDTO requestDTO =
            new TransferRequestDTO(
                    1L,
                    2L,
                    new BigDecimal("300"),
                    "Payment"
            );


    when(accountRepository.findById(1L))
            .thenReturn(Optional.empty());



    assertThrows(
            ResourceNotFoundException.class,
            () -> transactionService.transfer(requestDTO)
    );



    verify(accountRepository, never())
            .save(any(Account.class));


    verify(transactionRepository, never())
            .saveAll(anyList());

  }

  @Test
  void transfer_ShouldThrowException_WhenDestinationAccountDoesNotExist(){

    TransferRequestDTO requestDTO =
            new TransferRequestDTO(
                    1L,
                    2L,
                    new BigDecimal("300"),
                    "Payment"
            );


    Account sourceAccount = new Account();

    sourceAccount.setId(1L);
    sourceAccount.setStatus(AccountStatus.ACTIVE);
    sourceAccount.setBalance(new BigDecimal("1000"));



    when(accountRepository.findById(1L))
            .thenReturn(Optional.of(sourceAccount));



    when(accountRepository.findById(2L))
            .thenReturn(Optional.empty());



    assertThrows(
            ResourceNotFoundException.class,
            () -> transactionService.transfer(requestDTO)
    );



    verify(accountRepository, never())
            .save(any(Account.class));


    verify(transactionRepository, never())
            .saveAll(anyList());

  }

  @Test
  void transfer_ShouldThrowException_WhenSourceAccountIsInactive(){

    TransferRequestDTO requestDTO =
            new TransferRequestDTO(
                    1L,
                    2L,
                    new BigDecimal("300"),
                    "Payment"
            );


    Account sourceAccount = new Account();

    sourceAccount.setId(1L);
    sourceAccount.setStatus(AccountStatus.CLOSED);
    sourceAccount.setBalance(new BigDecimal("1000"));



    Account destinationAccount = new Account();

    destinationAccount.setId(2L);
    destinationAccount.setStatus(AccountStatus.ACTIVE);
    destinationAccount.setBalance(new BigDecimal("500"));



    when(accountRepository.findById(1L))
            .thenReturn(Optional.of(sourceAccount));



    when(accountRepository.findById(2L))
            .thenReturn(Optional.of(destinationAccount));



    assertThrows(
            InvalidOperationException.class,
            () -> transactionService.transfer(requestDTO)
    );



    verify(accountRepository, never())
            .save(any(Account.class));


    verify(transactionRepository, never())
            .saveAll(anyList());

  }

  @Test
  void transfer_ShouldThrowException_WhenDestinationAccountIsInactive(){

    TransferRequestDTO requestDTO =
            new TransferRequestDTO(
                    1L,
                    2L,
                    new BigDecimal("300"),
                    "Payment"
            );


    Account sourceAccount = new Account();

    sourceAccount.setId(1L);
    sourceAccount.setStatus(AccountStatus.ACTIVE);
    sourceAccount.setBalance(new BigDecimal("1000"));



    Account destinationAccount = new Account();

    destinationAccount.setId(2L);
    destinationAccount.setStatus(AccountStatus.CLOSED);
    destinationAccount.setBalance(new BigDecimal("500"));



    when(accountRepository.findById(1L))
            .thenReturn(Optional.of(sourceAccount));



    when(accountRepository.findById(2L))
            .thenReturn(Optional.of(destinationAccount));



    assertThrows(
            InvalidOperationException.class,
            () -> transactionService.transfer(requestDTO)
    );



    verify(accountRepository, never())
            .save(any(Account.class));


    verify(transactionRepository, never())
            .saveAll(anyList());

  }
}