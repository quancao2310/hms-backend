package com.example.hms.medicalrecordservice.patientinternalinfo.dto;

import com.example.hms.enums.MaritalStatus;
import com.example.hms.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponseDTO {
    private UUID id;
    private String fullName;
    private String ssn;
    private LocalDate dateOfBirth;
    private Sex sex;
    private String nationality;
    private String phoneNumber;
    private String address;
    private String occupation;
    private MaritalStatus maritalStatus;
    private Instant createdAt;
}