package com.example.hms.medicalrecordservice.patientinternalinfo.dto;

import com.example.hms.medicalrecordservice.medicalinfo.dto.MedicalInfoMutationRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.constant.PatientErrorMessages;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientCreateRequestDTO {

    @NotNull(message = PatientErrorMessages.PATIENT_INFORMATION_REQUIRED)
    @Valid
    private PatientMutationRequestDTO patientInfo;

    @Valid
    private MedicalInfoMutationRequestDTO medicalInfo;
}