package com.example.hms.staffservice.staff.dto;

import com.example.hms.enums.WorkingStatus;
import com.example.hms.staffservice.common.validator.annotation.ValidEnum;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusDTO(
    @NotNull(message = "Status is required")
    @ValidEnum(enumClass = WorkingStatus.class)
    String status
) {
}
