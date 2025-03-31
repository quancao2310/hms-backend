package com.example.hms.patientfacingservice.auth.dtos;

import com.example.hms.patientfacingservice.auth.validators.annotations.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequestDTO(
        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,

        @NotBlank(message = "Password is required")
        @ValidPassword
        String password,

        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "SSN is required")
        String ssn,

        @NotBlank(message = "Phone number is required")
        String phoneNumber
) {
}
