package com.example.hms.staffservice.staffmanagement.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class NurseUpdateRequest extends StaffUpdateRequest {
    private String licenseNumber;
    private String qualification;
} 