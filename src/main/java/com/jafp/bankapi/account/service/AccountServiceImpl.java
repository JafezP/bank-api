package com.jafp.bankapi.account.service;

import com.jafp.bankapi.account.dto.AccountRequestDTO;
import com.jafp.bankapi.account.dto.AccountResponseDTO;
import com.jafp.bankapi.account.entity.Account;
import com.jafp.bankapi.account.entity.AccountStatus;
import com.jafp.bankapi.account.mapper.AccountMapper;
import com.jafp.bankapi.account.repository.AccountRepository;
import com.jafp.bankapi.client.entity.Client;
import com.jafp.bankapi.client.entity.ClientStatus;
import com.jafp.bankapi.client.repository.ClientRepository;
import com.jafp.bankapi.common.exception.DuplicateResourceException;
import com.jafp.bankapi.common.exception.InvalidOperationException;
import com.jafp.bankapi.common.exception.ResourceNotFoundException;
import com.jafp.bankapi.common.generator.AccountNumberGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final ClientRepository clientRepository;
  private final AccountMapper accountMapper;
  private final AccountNumberGenerator accountNumberGenerator;

  public AccountServiceImpl(
          AccountRepository accountRepository,
          ClientRepository clientRepository,
          AccountMapper accountMapper,
          AccountNumberGenerator accountNumberGenerator
  ) {
    this.accountRepository = accountRepository;
    this.clientRepository = clientRepository;
    this.accountMapper = accountMapper;
    this.accountNumberGenerator = accountNumberGenerator;
  }

  @Override
  public List<AccountResponseDTO> findAll() {

    return accountRepository.findAll()
            .stream()
            .map(accountMapper::toResponseDTO)
            .toList();
  }

  @Override
  public AccountResponseDTO findById(Long id) {

    Account account = accountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    return accountMapper.toResponseDTO(account);
  }

  @Override
  public AccountResponseDTO save(AccountRequestDTO requestDTO) {

    Client client = clientRepository.findById(requestDTO.clientId())
            .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

    if(client.getStatus() != ClientStatus.ACTIVE){
      throw new InvalidOperationException("Client is not active");
    }

    if (accountRepository.existsByClientAndAccountType(
            client,
            requestDTO.accountType())) {

      throw new DuplicateResourceException(
              "Client already has this account type"
      );
    }
    Account account = new Account();

    account.setAccountNumber(accountNumberGenerator.generate());
    account.setClient(client);
    account.setAccountType(requestDTO.accountType());
    account.initializeBalance();
    account.activateAccount();
    Account savedAccount = accountRepository.save(account);

    return accountMapper.toResponseDTO(savedAccount);
  }
}
