package com.example.hms.staffservice.staffmanagement.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class AdminDTO extends StaffAccountDTO {
    // Admin-specific fields can be added here if needed in the future
} 