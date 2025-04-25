package com.example.hms.medicalrecordservice.medicalrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecordResponseDTO {
    private UUID id;
    private String symptoms;
    private String diagnoses;
    private String treatments;
    private String notes;
    private UUID patientId;
    private String patientName;
    private UUID doctorId;
    private String doctorName;
    private Instant createdAt;
}