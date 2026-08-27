package com.jafp.bankapi.transaction.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositRequestDTO(

        @NotNull
        Long accountId,

        @NotNull
        @Positive
        BigDecimal amount,

        String description

) {}
