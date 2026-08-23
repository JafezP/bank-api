package com.jafp.bankapi.account.dto;

import java.math.BigDecimal;

public record AccountResponseDTO (
        String accountNumber,
        Long clientId,
        String clientName,
        String accountType,
        String accountStatus,
        BigDecimal balance
){ }
