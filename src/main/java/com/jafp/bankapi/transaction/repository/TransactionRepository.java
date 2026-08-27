package com.jafp.bankapi.transaction.repository;

import com.jafp.bankapi.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}
