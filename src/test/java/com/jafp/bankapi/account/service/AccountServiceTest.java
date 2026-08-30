package com.jafp.bankapi.account.service;

import com.jafp.bankapi.account.dto.AccountRequestDTO;
import com.jafp.bankapi.account.dto.AccountResponseDTO;
import com.jafp.bankapi.account.entity.Account;
import com.jafp.bankapi.account.entity.AccountType;
import com.jafp.bankapi.account.mapper.AccountMapper;
import com.jafp.bankapi.account.repository.AccountRepository;
import com.jafp.bankapi.client.entity.Client;
import com.jafp.bankapi.client.entity.ClientStatus;
import com.jafp.bankapi.client.repository.ClientRepository;
import com.jafp.bankapi.common.exception.DuplicateResourceException;
import com.jafp.bankapi.common.exception.InvalidOperationException;
import com.jafp.bankapi.common.exception.ResourceNotFoundException;
import com.jafp.bankapi.common.generator.AccountNumberGenerator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private ClientRepository clientRepository;

  @Mock
  private AccountMapper accountMapper;

  @Mock
  private AccountNumberGenerator accountNumberGenerator;

  @InjectMocks
  private AccountServiceImpl accountService;


  @Test
  void save_ShouldCreateAccount_WhenClientIsActive(){

    AccountRequestDTO requestDTO =
            new AccountRequestDTO(
                    1L,
                    AccountType.SAVINGS
            );


    Client client = new Client();

    client.setId(1L);
    client.setStatus(ClientStatus.ACTIVE);


    when(clientRepository.findById(1L))
            .thenReturn(Optional.of(client));


    when(accountRepository.existsByClientAndAccountType(
            client,
            AccountType.SAVINGS
    ))
            .thenReturn(false);


    when(accountNumberGenerator.generate())
            .thenReturn("123456789012");


    when(accountRepository.save(any(Account.class)))
            .thenAnswer(invocation ->
                    invocation.getArgument(0)
            );


    AccountResponseDTO responseDTO =
            new AccountResponseDTO(
                    "123456789012",
                    1L,
                    "Joel Fernandez",
                    "ACTIVE",
                    "ACTIVE",
                    BigDecimal.ZERO
            );
    when(accountMapper.toResponseDTO(any(Account.class))).thenReturn(responseDTO);
    AccountResponseDTO response = accountService.save(requestDTO);
    assertEquals("123456789012", response.accountNumber());
  }

  @Test
  void save_ShouldThrowException_WhenClientDoesNotExist() {
    AccountRequestDTO requestDTO =
            new AccountRequestDTO(
                    1L,
                    AccountType.SAVINGS
            );
    when(clientRepository.findById(1L))
            .thenReturn(Optional.empty());

    assertThrows(
            ResourceNotFoundException.class,
            () -> accountService.save(requestDTO)
    );

    verify(accountRepository, never())
            .save(any(Account.class));
  }

  @Test
  void save_ShouldThrowException_WhenClientIsNotActive() {

    AccountRequestDTO requestDTO =
            new AccountRequestDTO(
                    1L,
                    AccountType.SAVINGS
            );

    Client client = new Client();
    client.setId(1L);
    client.setStatus(ClientStatus.INACTIVE);

    when(clientRepository.findById(1L))
            .thenReturn(Optional.of(client));

    assertThrows(
            InvalidOperationException.class,
            () -> accountService.save(requestDTO)
    );

    verify(accountRepository, never())
            .save(any(Account.class));
  }

  @Test
  void save_ShouldThrowException_WhenAccountAlreadyExists() {

    AccountRequestDTO requestDTO =
            new AccountRequestDTO(
                    1L,
                    AccountType.SAVINGS
            );


    Client client = new Client();

    client.setId(1L);
    client.setStatus(ClientStatus.ACTIVE);

    when(clientRepository.findById(1L))
            .thenReturn(Optional.of(client));

    when(accountRepository.existsByClientAndAccountType(
            client,
            AccountType.SAVINGS
    ))
            .thenReturn(true);

    assertThrows(
            DuplicateResourceException.class,
            () -> accountService.save(requestDTO)
    );

    verify(accountRepository, never())
            .save(any(Account.class));

  }

  @Test
  void findById_ShouldReturnAccount_WhenAccountExists(){

    Account account = new Account();

    account.setId(1L);
    account.setAccountNumber("123456789012");
    account.setAccountType(AccountType.SAVINGS);

    when(accountRepository.findById(1L))
            .thenReturn(Optional.of(account));

    AccountResponseDTO responseDTO =
            new AccountResponseDTO(
                    "123456789012",
                    1L,
                    "Joel Fernandez",
                    "SAVINGS",
                    "ACTIVE",
                    BigDecimal.ZERO
            );

    when(accountMapper.toResponseDTO(account))
            .thenReturn(responseDTO);

    AccountResponseDTO response =
            accountService.findById(1L);

    assertEquals(
            "123456789012",
            response.accountNumber()
    );
  }

  @Test
  void findById_ShouldThrowException_WhenAccountDoesNotExist(){

    when(accountRepository.findById(1L))
            .thenReturn(Optional.empty());


    assertThrows(
            ResourceNotFoundException.class,
            () -> accountService.findById(1L)
    );


    verify(accountMapper, never())
            .toResponseDTO(any(Account.class));

  }
}