package com.example.hms.medicalrecordservice.patientinternalinfo.dto;

import com.example.hms.medicalrecordservice.medicalrecord.dto.MedicalRecordResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientWithLatestRecordDTO {
    private PatientResponseDTO patient;
    private MedicalRecordResponseDTO latestMedicalRecord;
}