package com.jafp.bankapi.security.controller;

import com.jafp.bankapi.security.dto.LoginRequestDTO;
import com.jafp.bankapi.security.dto.LoginResponseDTO;
import com.jafp.bankapi.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {


  private final AuthService authService;


  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(
          @Valid @RequestBody LoginRequestDTO request
  ){

    String token = authService.login(
            request.getUsername(),
            request.getPassword()
    );


    return ResponseEntity.ok(
            new LoginResponseDTO(token)
    );
  }

}