package com.example.hms.patientfacingservice.patientaccount.dtos;

import com.example.hms.patientfacingservice.auth.validators.annotations.ValidPassword;
import com.example.hms.patientfacingservice.patientaccount.validators.annotations.MatchingPassword;
import jakarta.validation.constraints.NotBlank;

@MatchingPassword(
        newPassword = "newPassword",
        confirmPassword = "confirmPassword"
)
public record UpdatePasswordDTO(
        @NotBlank(message = "Current password is required")
        String currentPassword,

        @ValidPassword
        String newPassword,

        @NotBlank(message = "Password confirmation is required")
        String confirmPassword
) {
}
