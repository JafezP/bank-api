package com.jafp.bankapi.security.config;

import com.jafp.bankapi.security.jwt.JwtAuthenticationFilter;
import com.jafp.bankapi.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

  private final CustomUserDetailsService customUserDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;


  public SecurityConfig(
          CustomUserDetailsService customUserDetailsService,
          PasswordEncoder passwordEncoder,
          JwtAuthenticationFilter jwtAuthenticationFilter
  ) {
    this.customUserDetailsService = customUserDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(
                    jwtAuthenticationFilter,
                    UsernamePasswordAuthenticationFilter.class
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/api/v1/auth/login",
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    ).permitAll()
                    .anyRequest().authenticated()
            );

    return http.build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(){

    DaoAuthenticationProvider provider =
            new DaoAuthenticationProvider(customUserDetailsService);

    provider.setPasswordEncoder(passwordEncoder);

    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(
          AuthenticationConfiguration configuration
  ) throws Exception {

    return configuration.getAuthenticationManager();
  }
}