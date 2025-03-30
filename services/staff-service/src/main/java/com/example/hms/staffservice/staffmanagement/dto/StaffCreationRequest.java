package com.example.hms.staffservice.staffmanagement.dto;

import com.example.hms.enums.Sex;
import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StaffCreationRequest {
    
    @NotBlank(message = "Full name is required")
    private String fullName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    // Password will be auto-generated
    private String password;
    
    @NotBlank(message = "SSN is required")
    private String ssn;
    
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    
    @NotNull(message = "Sex is required")
    private Sex sex;
    
    private String phoneNumber;
    private String nationality;
    private String address;
    
    @NotNull(message = "Role is required")
    private UserRole role;
    
    @NotNull(message = "Start working date is required")
    private LocalDate startWorkingDate;
    
    @NotNull(message = "Working status is required")
    private WorkingStatus status;
} 