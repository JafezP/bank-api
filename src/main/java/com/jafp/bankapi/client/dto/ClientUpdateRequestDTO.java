package com.jafp.bankapi.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientUpdateRequestDTO(

        @NotBlank
        String firstName,

        String secondName,

        @NotBlank
        String lastName,

        @NotBlank
        String motherLastName,

        @Email
        String email,

        String phoneNumber,

        @NotBlank
        String address
) {}
