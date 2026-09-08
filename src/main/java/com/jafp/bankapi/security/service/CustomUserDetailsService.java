package com.jafp.bankapi.security.service;

import com.jafp.bankapi.security.entity.AppUser;
import com.jafp.bankapi.security.repository.AppUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final AppUserRepository appUserRepository;

  public CustomUserDetailsService(AppUserRepository appUserRepository) {
    this.appUserRepository = appUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    AppUser appUser = appUserRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return User.builder()
            .username(appUser.getUsername())
            .password(appUser.getPassword())
            .roles(appUser.getRole().name())
            .build();
  }
}