package com.jafp.bankapi.account.repository;

import com.jafp.bankapi.account.entity.Account;
import com.jafp.bankapi.account.entity.AccountType;
import com.jafp.bankapi.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
  boolean existsByClientAndAccountType(Client client, AccountType accountType);
}
