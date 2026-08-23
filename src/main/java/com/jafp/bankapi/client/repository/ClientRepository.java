package com.jafp.bankapi.client.repository;

import com.jafp.bankapi.client.entity.Client;
import com.jafp.bankapi.client.entity.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
  boolean existsByDni(String dni);
  boolean existsByEmail(String email);
  boolean existsByEmailAndIdNot(String email, Long id);
  boolean existsByPhoneNumber(String phoneNumber);
  boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
}
