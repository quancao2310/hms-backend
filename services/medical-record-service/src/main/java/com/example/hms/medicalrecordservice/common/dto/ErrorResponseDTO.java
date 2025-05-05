package com.example.hms.medicalrecordservice.common.dto;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

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
