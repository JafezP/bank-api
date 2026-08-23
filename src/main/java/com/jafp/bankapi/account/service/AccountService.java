package com.jafp.bankapi.account.service;

import com.jafp.bankapi.account.dto.AccountRequestDTO;
import com.jafp.bankapi.account.dto.AccountResponseDTO;

import java.util.List;

public interface AccountService {
  List<AccountResponseDTO> findAll();
  AccountResponseDTO findById(Long id);
  AccountResponseDTO save(AccountRequestDTO accountRequestDTO);
}
