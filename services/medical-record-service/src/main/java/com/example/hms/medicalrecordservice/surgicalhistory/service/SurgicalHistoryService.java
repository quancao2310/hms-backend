package com.example.hms.medicalrecordservice.surgicalhistory.service;

import com.example.hms.medicalrecordservice.surgicalhistory.dto.SurgicalHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.surgicalhistory.dto.SurgicalHistoryResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface SurgicalHistoryService {
    Page<SurgicalHistoryResponseDTO> getSurgicalHistoriesPageByPatientId(UUID patientId, int page, int size);
    SurgicalHistoryResponseDTO getSurgicalHistoryById(UUID patientId, UUID surgicalHistoryId);
    SurgicalHistoryResponseDTO createSurgicalHistory(UUID patientId, SurgicalHistoryCreateRequestDTO request);
    SurgicalHistoryResponseDTO updateSurgicalHistory(UUID patientId, UUID surgicalHistoryId, SurgicalHistoryCreateRequestDTO request);
    void deleteSurgicalHistory(UUID patientId, UUID surgicalHistoryId);
}
