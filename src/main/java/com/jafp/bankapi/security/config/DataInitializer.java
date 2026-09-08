package com.jafp.bankapi.security.config;

import com.jafp.bankapi.security.entity.AppUser;
import com.jafp.bankapi.security.entity.Role;
import com.jafp.bankapi.security.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class DataInitializer implements CommandLineRunner {

  private final AppUserRepository appUserRepository;
  private final PasswordEncoder passwordEncoder;


  public DataInitializer(
          AppUserRepository appUserRepository,
          PasswordEncoder passwordEncoder
  ) {
    this.appUserRepository = appUserRepository;
    this.passwordEncoder = passwordEncoder;
  }


  @Override
  public void run(String... args) {

    if(appUserRepository.findByUsername("admin").isPresent()){
      return;
    }


    AppUser admin = new AppUser();

    admin.setUsername("admin");

    admin.setPassword(
            passwordEncoder.encode("Admin123")
    );

    admin.setRole(Role.ADMIN);

    admin.setEnabled(true);


    appUserRepository.save(admin);
  }
}