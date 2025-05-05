package com.example.hms.medicalrecordservice.pastdisease.service;

import com.example.hms.medicalrecordservice.pastdisease.dto.PastDiseaseCreateRequestDTO;
import com.example.hms.medicalrecordservice.pastdisease.dto.PastDiseaseResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PastDiseaseService {
    Page<PastDiseaseResponseDTO> getPastDiseasesPageByPatientId(UUID patientId, int page, int size);
    PastDiseaseResponseDTO getPastDiseaseById(UUID patientId, UUID pastDiseaseId);
    PastDiseaseResponseDTO createPastDisease(UUID patientId, PastDiseaseCreateRequestDTO request);
    PastDiseaseResponseDTO updatePastDisease(UUID patientId, UUID pastDiseaseId, PastDiseaseCreateRequestDTO request);
    void deletePastDisease(UUID patientId, UUID pastDiseaseId);
}