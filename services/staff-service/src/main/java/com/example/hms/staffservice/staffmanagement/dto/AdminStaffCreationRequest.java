package com.example.hms.staffservice.staffmanagement.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class AdminStaffCreationRequest extends StaffCreationRequest {
    // Admin-specific fields can be added here if needed in the future
}
