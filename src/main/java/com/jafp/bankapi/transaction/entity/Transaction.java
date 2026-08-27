package com.jafp.bankapi.transaction.entity;

import com.jafp.bankapi.account.entity.Account;
import com.jafp.bankapi.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;
  @Enumerated(EnumType.STRING)
  @Column(name = "transaction_type", nullable = false)
  private TransactionType transactionType;
  @Column(
          name = "amount",
          nullable = false,
          precision = 15,
          scale = 2
  )
  private BigDecimal amount;
  @Column(
          name = "balance_before",
          nullable = false,
          precision = 15,
          scale = 2
  )
  private BigDecimal balanceBefore;
  @Column(
          name = "balance_after",
          nullable = false,
          precision = 15,
          scale = 2
  )
  private BigDecimal balanceAfter;
  @Column(name = "transfer_reference")
  private String transferReference;
  @Column(
          name = "description",
          length = 255
  )
  private String description;
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private TransactionStatus status;

  public Transaction() {
  }

  public Transaction(Account account, TransactionType transactionType, BigDecimal amount, BigDecimal balanceBefore, BigDecimal balanceAfter, String transferReference, String description, TransactionStatus status) {
    this.account = account;
    this.transactionType = transactionType;
    this.amount = amount;
    this.balanceBefore = balanceBefore;
    this.balanceAfter = balanceAfter;
    this.transferReference = transferReference;
    this.description = description;
    this.status = status;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getBalanceBefore() {
    return balanceBefore;
  }

  public void setBalanceBefore(BigDecimal balanceBefore) {
    this.balanceBefore = balanceBefore;
  }

  public BigDecimal getBalanceAfter() {
    return balanceAfter;
  }

  public void setBalanceAfter(BigDecimal balanceAfter) {
    this.balanceAfter = balanceAfter;
  }

  public String getTransferReference() {
    return transferReference;
  }

  public void setTransferReference(String transferReference) {
    this.transferReference = transferReference;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TransactionStatus getStatus() {
    return status;
  }

  public void setStatus(TransactionStatus status) {
    this.status = status;
  }

  public void complete() {
    this.status = TransactionStatus.COMPLETED;
  }

  public void markAsFailed() {
    this.status = TransactionStatus.FAILED;
  }
}
