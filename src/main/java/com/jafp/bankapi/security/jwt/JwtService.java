package com.jafp.bankapi.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {


  private final String secretKey;

  private final long expirationTime;


  public JwtService(
          @Value("${jwt.secret-key}") String secretKey,
          @Value("${jwt.expiration}") long expirationTime
  ) {
    this.secretKey = secretKey;
    this.expirationTime = expirationTime;
  }


  public String generateToken(String username){

    return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(
                    new Date(System.currentTimeMillis() + expirationTime)
            )
            .signWith(getSigningKey())
            .compact();
  }


  public String extractUsername(String token){

    return extractClaims(token)
            .getSubject();
  }


  public boolean isTokenValid(
          String token,
          String username
  ){

    String tokenUsername = extractUsername(token);

    return tokenUsername.equals(username)
            && !isTokenExpired(token);
  }


  private boolean isTokenExpired(String token){

    return extractClaims(token)
            .getExpiration()
            .before(new Date());
  }


  private Claims extractClaims(String token){

    return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }


  private SecretKey getSigningKey(){

    return Keys.hmacShaKeyFor(
            secretKey.getBytes(StandardCharsets.UTF_8)
    );
  }
}