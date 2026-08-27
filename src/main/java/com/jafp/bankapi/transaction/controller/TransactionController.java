package com.jafp.bankapi.transaction.controller;

import com.jafp.bankapi.transaction.dto.DepositRequestDTO;
import com.jafp.bankapi.transaction.dto.TransactionResponseDTO;
import com.jafp.bankapi.transaction.dto.TransferRequestDTO;
import com.jafp.bankapi.transaction.dto.WithdrawalRequestDTO;
import com.jafp.bankapi.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/deposit")
  public ResponseEntity<TransactionResponseDTO> deposit(
          @Valid @RequestBody DepositRequestDTO requestDTO
  ){

    return ResponseEntity.ok(
            transactionService.deposit(requestDTO)
    );
  }

  @PostMapping("/withdraw")
  public ResponseEntity<TransactionResponseDTO> withdraw(
          @Valid @RequestBody WithdrawalRequestDTO requestDTO
  ){

    return ResponseEntity.ok(
            transactionService.withdraw(requestDTO)
    );
  }

  @PostMapping("/transfer")
  public ResponseEntity<TransactionResponseDTO> transfer(
          @Valid @RequestBody TransferRequestDTO requestDTO
  ){

    return ResponseEntity.ok(
            transactionService.transfer(requestDTO)
    );
  }

  @GetMapping("/account/{accountId}")
  public ResponseEntity<List<TransactionResponseDTO>> findByAccount(
          @PathVariable Long accountId
  ){

    return ResponseEntity.ok(
            transactionService.findByAccount(accountId)
    );
  }
}