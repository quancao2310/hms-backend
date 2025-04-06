package com.example.hms.staffservice.staff.dto;

import com.example.hms.enums.Sex;
import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public record CreateStaffDTO(
    @NotBlank(message = "Full name is required")
    String fullName,
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email,
    
    @NotBlank(message = "SSN is required")
    String ssn,
    
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    LocalDate dateOfBirth,
    
    @NotNull(message = "Sex is required")
    Sex sex,
    
    String phoneNumber,
    String nationality,
    String address,
    String biography,
    
    @NotNull(message = "Role is required")
    UserRole role,
    
    @NotNull(message = "Start working date is required")
    LocalDate startWorkingDate,
    
    @NotNull(message = "Status is required")
    WorkingStatus status,
    
    // Fields for Doctor and Nurse
    String licenseNumber,
    String qualification,
    String department,
    
    // Fields for Doctor only
    Set<String> specializations,
    Set<String> services
) {
    public CreateStaffDTO {
        if (role == UserRole.DOCTOR && (specializations == null || specializations.isEmpty())) {
            throw new IllegalArgumentException("Doctor must have at least one specialization");
        }
    }
} 