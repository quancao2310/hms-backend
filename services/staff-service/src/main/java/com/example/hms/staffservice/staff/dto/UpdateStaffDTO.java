package com.example.hms.staffservice.staff.dto;

import com.example.hms.enums.Sex;
import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.Set;

public record UpdateStaffDTO(
    String fullName,
    
    @Email(message = "Email must be valid")
    String email,
    
    String ssn,
    
    @Past(message = "Date of birth must be in the past")
    LocalDate dateOfBirth,
    
    Sex sex,
    String phoneNumber,
    String nationality,
    String address,
    String biography,
    
    LocalDate startWorkingDate,
    WorkingStatus status,
    
    // Fields for Doctor and Nurse
    String licenseNumber,
    String qualification,
    String department,
    
    // Fields for Doctor only
    Set<String> specializations,
    Set<String> services
) {
    public UpdateStaffDTO {
        // We don't allow changing the role
        if (UserRole.ADMIN.equals(UserRole.valueOf(System.getProperty("role")))) {
            throw new IllegalArgumentException("Admin role is not allowed to be updated");
        }
    }
} 