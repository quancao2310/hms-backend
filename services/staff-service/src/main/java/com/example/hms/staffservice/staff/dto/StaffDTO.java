package com.example.hms.staffservice.staff.dto;

import com.example.hms.enums.Sex;
import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Builder
public record StaffDTO(
        UUID id,
        String fullName,
        String email,
        String ssn,
        LocalDate dateOfBirth,
        Sex sex,
        String phoneNumber,
        String nationality,
        String address,
        String biography,
        UserRole role,
        LocalDate startWorkingDate,
        WorkingStatus status,
        Instant createdAt,
        Instant lastLoginAt,
        String licenseNumber,
        String qualification,
        String department,
        Set<String> specializations,
        Set<String> services
) {
}
