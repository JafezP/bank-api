package com.jafp.bankapi.client.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ClientRequestDTO (
        @NotBlank
        String firstName,

        String secondName,

        @NotBlank
        String lastName,

        @NotBlank
        String motherLastName,

        @NotBlank
        @Pattern(
                regexp = "\\d{8}",
                message = "DNI must contain exactly 8 digits"
        )
        String dni,

        @NotBlank
        @Email
        String email,

        String phoneNumber,

        @NotBlank
        String address,

        @NotNull
        LocalDate birthDate
) {}
