package com.example.hms.staffservice.staff.dto;

import com.example.hms.enums.WorkingStatus;
import jakarta.validation.constraints.Min;

public record StaffFilterDTO(
    String fullName,
    WorkingStatus status,
    String department,
    String sortBy,
    String sortDirection,
    @Min(value = 0, message = "Page number must be greater than or equal to 0")
    Integer page,
    @Min(value = 1, message = "Page size must be greater than 0")
    Integer size
) {
    public StaffFilterDTO {
        // Default values if not provided
        if (page == null) page = 0;
        if (size == null) size = 10;
        if (sortBy == null) sortBy = "fullName";
        if (sortDirection == null) sortDirection = "asc";
    }
} 