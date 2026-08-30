package com.jafp.bankapi.account.controller;

import com.jafp.bankapi.account.dto.AccountRequestDTO;
import com.jafp.bankapi.account.dto.AccountResponseDTO;
import com.jafp.bankapi.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Accounts",
        description = "Operations related to bank accounts"
)
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }


  @GetMapping
  @Operation(
          summary = "Get all accounts"
  )
  @ApiResponse(
          responseCode = "200",
          description = "Accounts found"
  )
  public ResponseEntity<List<AccountResponseDTO>> findAll() {
    return ResponseEntity.ok(accountService.findAll());
  }

  @GetMapping("/{id}")
  @Operation(
          summary = "Find account by id"
  )
  @ApiResponses({

          @ApiResponse(
                  responseCode = "200",
                  description = "Account found"
          ),

          @ApiResponse(
                  responseCode = "404",
                  description = "Account not found"
          )

  })
  public ResponseEntity<AccountResponseDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(accountService.findById(id));
  }

  @PostMapping
  @Operation(
          summary = "Create account",
          description = "Creates a bank account for an existing client"
  )
  @ApiResponses({

          @ApiResponse(
                  responseCode = "201",
                  description = "Account created"
          ),

          @ApiResponse(
                  responseCode = "404",
                  description = "Client not found"
          )

  })
  public ResponseEntity<AccountResponseDTO> save(
          @Valid @RequestBody AccountRequestDTO requestDTO
  ) {
    AccountResponseDTO response = accountService.save(requestDTO);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
  }
}
