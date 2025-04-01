package com.example.hms.patientfacingservice.patientaccount.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateAccountDTO(
        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,

        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "SSN is required")
        String ssn,

        @NotBlank(message = "Phone number is required")
        String phoneNumber
) {
}
