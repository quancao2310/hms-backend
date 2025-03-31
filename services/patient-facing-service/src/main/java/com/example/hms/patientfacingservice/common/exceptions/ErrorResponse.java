package com.example.hms.patientfacingservice.common.exceptions;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
        int statusCode,
        String message,
        Map<String, String> errors,
        Instant timestamp
) {
    public ErrorResponse(int statusCode, String message, Map<String, String> errors) {
        this(statusCode, message, errors, Instant.now());
    }

    public ErrorResponse(int statusCode, String message) {
        this(statusCode, message, null);
    }
}
