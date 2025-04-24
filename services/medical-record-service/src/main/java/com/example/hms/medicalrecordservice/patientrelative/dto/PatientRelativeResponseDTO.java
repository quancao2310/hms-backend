package com.example.hms.medicalrecordservice.patientrelative.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRelativeResponseDTO {
    private UUID id;
    private String fullName;
    private String relationship;
    private String phoneNumber;
}