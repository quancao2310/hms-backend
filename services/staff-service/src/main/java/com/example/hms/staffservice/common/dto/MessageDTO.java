package com.example.hms.staffservice.common.dto;

public record MessageDTO(
        String message
) {
    public MessageDTO {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("Message cannot be null or blank");
        }
    }
}
