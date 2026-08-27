package com.jafp.bankapi.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long id,
        String accountNumber,
        String transactionType,
        String transferReference,
        BigDecimal amount,
        BigDecimal balanceBefore,
        BigDecimal balanceAfter,
        String description,
        String status,
        LocalDateTime createdAt
) {}
