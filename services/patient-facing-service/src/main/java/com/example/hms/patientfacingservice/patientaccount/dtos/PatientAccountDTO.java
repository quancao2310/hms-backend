package com.example.hms.patientfacingservice.patientaccount.dtos;

import java.time.Instant;
import java.util.UUID;

public record PatientAccountDTO(
        UUID id,
        String fullName,
        String email,
        String ssn,
        String phoneNumber,
        Instant createdAt,
        Instant lastLoginAt
) {
}
