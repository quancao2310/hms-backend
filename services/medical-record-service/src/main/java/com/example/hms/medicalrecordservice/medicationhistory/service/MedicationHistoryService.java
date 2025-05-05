package com.example.hms.medicalrecordservice.medicationhistory.service;

import com.example.hms.medicalrecordservice.medicationhistory.dto.MedicationHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.medicationhistory.dto.MedicationHistoryResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MedicationHistoryService {
    Page<MedicationHistoryResponseDTO> getMedicationHistoriesPageByPatientId(UUID patientId, int page, int size);
    MedicationHistoryResponseDTO getMedicationHistoryById(UUID patientId, UUID medicationHistoryId);
    MedicationHistoryResponseDTO createMedicationHistory(UUID patientId, MedicationHistoryCreateRequestDTO request);
    MedicationHistoryResponseDTO updateMedicationHistory(UUID patientId, UUID medicationHistoryId, MedicationHistoryCreateRequestDTO request);
    void deleteMedicationHistory(UUID patientId, UUID medicationHistoryId);
}