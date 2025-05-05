package com.example.hms.medicalrecordservice.medicalrecord.dto;

import com.example.hms.medicalrecordservice.medicalrecord.constant.MedicalRecordErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecordMutationRequestDTO {

    @NotBlank(message = MedicalRecordErrorMessages.SYMPTOMS_REQUIRED)
    private String symptoms;

    @NotBlank(message = MedicalRecordErrorMessages.DIAGNOSES_REQUIRED)
    private String diagnoses;

    @NotBlank(message = MedicalRecordErrorMessages.TREATMENTS_REQUIRED)
    private String treatments;

    private String notes;

    @NotNull(message = MedicalRecordErrorMessages.DOCTOR_ID_REQUIRED)
    private UUID doctorId;
}