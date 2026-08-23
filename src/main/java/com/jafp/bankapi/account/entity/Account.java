package com.jafp.bankapi.account.entity;

import com.jafp.bankapi.client.entity.Client;
import com.jafp.bankapi.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity{
  @Column(
          name = "account_number",
          nullable = false,
          unique = true
  )
  private String accountNumber;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;
  @Enumerated(EnumType.STRING)
  @Column(
          name = "account_type",
          nullable = false
  )
  private AccountType accountType;
  @Column(
          name = "balance",
          nullable = false,
          precision = 15,
          scale = 2
  )
  private BigDecimal balance;
  @Enumerated(EnumType.STRING)
  @Column(
          name = "status",
          nullable = false
  )
  private AccountStatus status;

  public Account() {
  }

  public Account(String accountNumber, Client client, AccountType accountType, BigDecimal balance, AccountStatus status) {
    this.accountNumber = accountNumber;
    this.client = client;
    this.accountType = accountType;
    this.balance = balance;
    this.status = status;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public AccountStatus getStatus() {
    return status;
  }

  public void setStatus(AccountStatus status) {
    this.status = status;
  }

  public void activateAccount(){
    this.status = AccountStatus.ACTIVE;
  }

  public void initializeBalance(){
    this.balance = BigDecimal.ZERO;
  }
}
