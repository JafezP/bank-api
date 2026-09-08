package com.jafp.bankapi.security.repository;

import com.jafp.bankapi.security.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  Optional<AppUser> findByUsername(String username);
}
