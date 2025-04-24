package com.example.hms.medicalrecordservice.medicalinfo.service;

import com.example.hms.medicalrecordservice.medicalinfo.dto.MedicalInfoMutationRequestDTO;
import com.example.hms.medicalrecordservice.medicalinfo.dto.MedicalInfoResponseDTO;

import java.util.UUID;

public interface MedicalInfoService {
    MedicalInfoResponseDTO getPatientMedicalInfo(UUID patientId);
    MedicalInfoResponseDTO updatePatientMedicalInfo(UUID patientId, MedicalInfoMutationRequestDTO request);
}
