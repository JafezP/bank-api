package com.jafp.bankapi.security.service;

import com.jafp.bankapi.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  public AuthService(
          AuthenticationManager authenticationManager,
          JwtService jwtService
  ) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  public String login(
          String username,
          String password
  ){

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    username,
                    password
            )
    );

    return jwtService.generateToken(username);
  }
}