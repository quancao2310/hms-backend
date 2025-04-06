package com.example.hms.staffservice.staff.dto;

import com.example.hms.enums.Sex;
import com.example.hms.enums.WorkingStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

// DTO for partial updates via PATCH. Only non-empty Optionals will be considered for update.
public record PatchStaffDTO(
    Optional<String> fullName,

    Optional<String> email,

    Optional<String> ssn,

    Optional<LocalDate> dateOfBirth,

    Optional<Sex> sex,
    Optional<String> phoneNumber,
    Optional<String> nationality,
    Optional<String> address,
    Optional<String> biography,

    Optional<LocalDate> startWorkingDate,
    Optional<WorkingStatus> status,

    // Fields for Doctor and Nurse
    Optional<String> licenseNumber,
    Optional<String> qualification,
    Optional<String> department,

    // Fields for Doctor only
    Optional<Set<String>> specializations,
    Optional<Set<String>> services
) {
} 