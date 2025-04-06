package com.example.hms.staffservice.staff.dto;

import com.example.hms.enums.WorkingStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusDTO(
    @NotNull(message = "Status is required")
    WorkingStatus status
) {
    public UpdateStatusDTO {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
    }
} 