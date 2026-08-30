package com.jafp.bankapi.transaction.controller;

import com.jafp.bankapi.transaction.dto.DepositRequestDTO;
import com.jafp.bankapi.transaction.dto.TransactionResponseDTO;
import com.jafp.bankapi.transaction.dto.TransferRequestDTO;
import com.jafp.bankapi.transaction.dto.WithdrawalRequestDTO;
import com.jafp.bankapi.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Transactions",
        description = "Operations related to financial transactions"
)
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }


  @PostMapping("/deposit")
  @Operation(
          summary = "Deposit money",
          description = "Adds money to an active account"
  )
  @ApiResponses({

          @ApiResponse(
                  responseCode = "200",
                  description = "Deposit completed"
          ),

          @ApiResponse(
                  responseCode = "404",
                  description = "Account not found"
          ),

          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid operation"
          )

  })
  public ResponseEntity<TransactionResponseDTO> deposit(
          @Valid @RequestBody DepositRequestDTO requestDTO
  ){
    return ResponseEntity.ok(transactionService.deposit(requestDTO));
  }

  @PostMapping("/withdraw")
  @Operation(
          summary = "Withdraw money",
          description = "Withdraws money from an active account"
  )
  public ResponseEntity<TransactionResponseDTO> withdraw(
          @Valid @RequestBody WithdrawalRequestDTO requestDTO
  ){
    return ResponseEntity.ok(transactionService.withdraw(requestDTO));
  }

  @PostMapping("/transfer")
  @Operation(
          summary = "Transfer money",
          description = "Transfers money between two accounts"
  )
  @ApiResponses({

          @ApiResponse(
                  responseCode = "200",
                  description = "Transfer completed"
          ),

          @ApiResponse(
                  responseCode = "400",
                  description = "Invalid transfer"
          ),

          @ApiResponse(
                  responseCode = "404",
                  description = "Account not found"
          )

  })
  public ResponseEntity<TransactionResponseDTO> transfer(
          @Valid @RequestBody TransferRequestDTO requestDTO
  ){
    return ResponseEntity.ok(transactionService.transfer(requestDTO));
  }

  @GetMapping("/account/{accountId}")
  @Operation(
          summary = "Get account transactions",
          description = "Returns transaction history ordered by date"
  )
  public ResponseEntity<List<TransactionResponseDTO>> findByAccount(
          @PathVariable Long accountId
  ){

    return ResponseEntity.ok(
            transactionService.findByAccount(accountId)
    );
  }
}