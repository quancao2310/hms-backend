package com.example.hms.staffservice.staff.dto;

import com.example.hms.enums.Sex;
import com.example.hms.enums.WorkingStatus;
import com.example.hms.staffservice.common.validator.annotation.ValidEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.Set;

public record UpdateStaffDTO(
        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "SSN is required")
        String ssn,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        @NotNull(message = "Sex is required")
        @ValidEnum(enumClass = Sex.class)
        String sex,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotBlank(message = "Nationality is required")
        String nationality,

        @NotBlank(message = "Address is required")
        String address,

        String biography,

        @NotNull(message = "Start working date is required")
        LocalDate startWorkingDate,

        @NotNull(message = "Status is required")
        @ValidEnum(enumClass = WorkingStatus.class)
        String status,

        // Fields for Doctor and Nurse
        String licenseNumber,
        String qualification,
        String department,

        // Fields for Doctor only
        Set<String> specializations,
        Set<String> services
) {
}
