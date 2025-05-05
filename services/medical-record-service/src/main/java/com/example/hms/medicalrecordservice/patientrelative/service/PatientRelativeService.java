package com.example.hms.medicalrecordservice.patientrelative.service;

import com.example.hms.medicalrecordservice.patientrelative.dto.PatientRelativeCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientrelative.dto.PatientRelativeResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PatientRelativeService {
    Page<PatientRelativeResponseDTO> getPatientRelativesPageByPatientId(UUID patientId, int page, int size);
    PatientRelativeResponseDTO getPatientRelativeById(UUID patientId, UUID patientRelativeId);
    PatientRelativeResponseDTO createPatientRelative(UUID patientId, PatientRelativeCreateRequestDTO request);
    PatientRelativeResponseDTO updatePatientRelative(UUID patientId, UUID patientRelativeId, PatientRelativeCreateRequestDTO request);
    void deletePatientRelative(UUID patientId, UUID patientRelativeId);
}