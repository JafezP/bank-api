package com.jafp.bankapi.transaction.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequestDTO(

        @NotNull
        Long sourceAccountId,

        @NotNull
        Long destinationAccountId,

        @NotNull
        @Positive
        BigDecimal amount,

        String description

) {}
