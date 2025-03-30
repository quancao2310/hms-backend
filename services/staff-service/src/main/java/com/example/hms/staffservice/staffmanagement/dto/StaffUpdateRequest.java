package com.example.hms.staffservice.staffmanagement.dto;

import com.example.hms.enums.Sex;
import com.example.hms.enums.WorkingStatus;
import jakarta.validation.constraints.Email;
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
public class StaffUpdateRequest {
    
    private String fullName;
    
    @Email(message = "Email should be valid")
    private String email;
    
    private String ssn;
    
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    
    private Sex sex;
    private String phoneNumber;
    private String nationality;
    private String address;
    private LocalDate startWorkingDate;
    private WorkingStatus status;
} 