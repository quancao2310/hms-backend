package com.example.hms.staffservice.staffmanagement.dto;

import com.example.hms.enums.Sex;
import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class StaffAccountDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String ssn;
    private LocalDate dateOfBirth;
    private Sex sex;
    private String phoneNumber;
    private String nationality;
    private String address;
    private UserRole role;
    private LocalDate startWorkingDate;
    private WorkingStatus status;
    private Instant createdAt;
    private Instant lastLoginAt;
}
