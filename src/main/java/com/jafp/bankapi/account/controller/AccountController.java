package com.jafp.bankapi.account.controller;

import com.jafp.bankapi.account.dto.AccountRequestDTO;
import com.jafp.bankapi.account.dto.AccountResponseDTO;
import com.jafp.bankapi.account.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping
  public ResponseEntity<List<AccountResponseDTO>> findAll() {
    return ResponseEntity.ok(accountService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<AccountResponseDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(accountService.findById(id));
  }

  @PostMapping
  public ResponseEntity<AccountResponseDTO> save(
          @Valid @RequestBody AccountRequestDTO requestDTO
  ) {
    AccountResponseDTO response = accountService.save(requestDTO);
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
  }
}
