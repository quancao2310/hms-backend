package com.example.hms.staffservice.staffmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class NurseCreationRequest extends StaffCreationRequest {
    
    @NotBlank(message = "License number is required")
    private String licenseNumber;
    
    @NotBlank(message = "Qualification is required")
    private String qualification;
} 