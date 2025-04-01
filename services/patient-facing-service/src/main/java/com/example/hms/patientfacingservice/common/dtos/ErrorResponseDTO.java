package com.example.hms.patientfacingservice.common.dtos;

import java.time.Instant;
import java.util.Set;
import java.util.Map;

public record ErrorResponseDTO(
        int statusCode,
        String message,
        Map<String, Set<String>> errors,
        Instant timestamp
) {
    public ErrorResponseDTO(int statusCode, String message, Map<String, Set<String>> errors) {
        this(statusCode, message, errors, Instant.now());
    }

    public ErrorResponseDTO(int statusCode, String message) {
        this(statusCode, message, null);
    }
}
