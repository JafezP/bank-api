package com.jafp.bankapi.transaction.service;

import com.jafp.bankapi.transaction.dto.DepositRequestDTO;
import com.jafp.bankapi.transaction.dto.TransactionResponseDTO;
import com.jafp.bankapi.transaction.dto.TransferRequestDTO;
import com.jafp.bankapi.transaction.dto.WithdrawalRequestDTO;

import java.util.List;

public interface TransactionService {

  TransactionResponseDTO deposit(DepositRequestDTO requestDTO);
  TransactionResponseDTO withdraw(WithdrawalRequestDTO requestDTO);
  TransactionResponseDTO transfer(TransferRequestDTO requestDTO);
  List<TransactionResponseDTO> findByAccount(Long accountId);
}
