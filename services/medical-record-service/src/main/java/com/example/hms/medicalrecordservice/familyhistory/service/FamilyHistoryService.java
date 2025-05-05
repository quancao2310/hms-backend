package com.example.hms.medicalrecordservice.familyhistory.service;

import com.example.hms.medicalrecordservice.familyhistory.dto.FamilyHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.familyhistory.dto.FamilyHistoryResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface FamilyHistoryService {
    Page<FamilyHistoryResponseDTO> getFamilyHistoriesPageByPatientId(UUID patientId, int page, int size);
    FamilyHistoryResponseDTO getFamilyHistoryById(UUID patientId, UUID familyHistoryId);
    FamilyHistoryResponseDTO createFamilyHistory(UUID patientId, FamilyHistoryCreateRequestDTO request);
    FamilyHistoryResponseDTO updateFamilyHistory(UUID patientId, UUID familyHistoryId, FamilyHistoryCreateRequestDTO request);
    void deleteFamilyHistory(UUID patientId, UUID familyHistoryId);
}