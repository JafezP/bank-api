package com.jafp.bankapi.account.dto;

import com.jafp.bankapi.account.entity.AccountType;
import jakarta.validation.constraints.NotNull;

public record AccountRequestDTO(
        @NotNull
        Long clientId,
        @NotNull
        AccountType accountType
){}
