package com.jafp.bankapi.client.dto;

import java.time.LocalDate;

public record ClientResponseDTO (
        String dni,
        String firstName,
        String secondName,
        String lastName,
        String motherLastName,
        String email,
        String phoneNumber,
        String address,
        LocalDate birthDate,
        String status
){
}
