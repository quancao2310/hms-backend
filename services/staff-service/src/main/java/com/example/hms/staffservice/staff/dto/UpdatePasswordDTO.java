package com.example.hms.staffservice.staff.dto;

import com.example.hms.staffservice.auth.validator.annotation.ValidPassword;
import com.example.hms.staffservice.staff.validator.annotation.MatchingPassword;
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
