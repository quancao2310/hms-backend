package com.example.hms.medicalrecordservice.medicalrecord.service;

import com.example.hms.medicalrecordservice.medicalrecord.dto.MedicalRecordMutationRequestDTO;
import com.example.hms.medicalrecordservice.medicalrecord.dto.MedicalRecordResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MedicalRecordService {
    Page<MedicalRecordResponseDTO> getMedicalRecordsByPatientId(UUID patientId, int page, int size);
    MedicalRecordResponseDTO getMedicalRecordById(UUID patientId, UUID recordId);
    MedicalRecordResponseDTO createMedicalRecord(UUID patientId, MedicalRecordMutationRequestDTO requestDTO);
    MedicalRecordResponseDTO updateMedicalRecord(UUID patientId, UUID recordId, MedicalRecordMutationRequestDTO requestDTO);
    void deleteMedicalRecord(UUID patientId, UUID recordId);
}