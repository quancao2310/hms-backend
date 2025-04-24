package com.example.hms.medicalrecordservice.medicationhistory.service.impl;

import com.example.hms.medicalrecordservice.common.constant.CommonErrorMessages;
import com.example.hms.medicalrecordservice.common.exception.ResourceNotFoundException;
import com.example.hms.medicalrecordservice.medicationhistory.constant.MedicationHistoryErrorMessages;
import com.example.hms.medicalrecordservice.medicationhistory.dto.MedicationHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.medicationhistory.dto.MedicationHistoryResponseDTO;
import com.example.hms.medicalrecordservice.medicationhistory.repository.MedicationHistoryRepository;
import com.example.hms.medicalrecordservice.medicationhistory.service.MedicationHistoryMapper;
import com.example.hms.medicalrecordservice.medicationhistory.service.MedicationHistoryService;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
import com.example.hms.models.internal.medicalrecord.MedicalHistory;
import com.example.hms.models.internal.medicalrecord.MedicationHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicationHistoryServiceImpl implements MedicationHistoryService {

    private final MedicationHistoryRepository medicationHistoryRepository;
    private final MedicationHistoryMapper medicationHistoryMapper;
    private final PatientService patientService;

    @Override
    @Transactional(readOnly = true)
    public Page<MedicationHistoryResponseDTO> getMedicationHistoriesPageByPatientId(UUID patientId, int page, int size) {
        patientService.getPatientById(patientId); // Ensure patient exists
        Pageable pageable = PageRequest.of(page, size);
        Page<MedicationHistory> medicationHistoryPage = medicationHistoryRepository.findPageByPatientId(patientId, pageable);
        return medicationHistoryPage.map(medicationHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicationHistoryResponseDTO getMedicationHistoryById(UUID patientId, UUID medicationHistoryId) {
        MedicationHistory medicationHistory = medicationHistoryRepository.findByPatientIdAndMedicationHistoryId(patientId, medicationHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MedicationHistoryErrorMessages.MEDICATION_HISTORY_NOT_FOUND, medicationHistoryId, patientId)));

        return medicationHistoryMapper.toDto(medicationHistory);
    }

    @Override
    @Transactional
    public MedicationHistoryResponseDTO createMedicationHistory(UUID patientId, MedicationHistoryCreateRequestDTO request) {
        MedicalHistory medicalHistory = medicationHistoryRepository.findMedicalHistoryByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(CommonErrorMessages.MEDICAL_HISTORY_NOT_FOUND, patientId)));

        MedicationHistory medicationHistory = medicationHistoryMapper.toEntity(request);
        medicationHistory.setMedicalHistory(medicalHistory);

        MedicationHistory savedMedicationHistory = medicationHistoryRepository.save(medicationHistory);

        return medicationHistoryMapper.toDto(savedMedicationHistory);
    }

    @Override
    @Transactional
    public MedicationHistoryResponseDTO updateMedicationHistory(UUID patientId, UUID medicationHistoryId,
                                                                MedicationHistoryCreateRequestDTO request) {
        MedicationHistory existingMedicationHistory = medicationHistoryRepository.findByPatientIdAndMedicationHistoryId(patientId, medicationHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MedicationHistoryErrorMessages.MEDICATION_HISTORY_NOT_FOUND, medicationHistoryId, patientId)));

        existingMedicationHistory.setName(request.getName());
        existingMedicationHistory.setDosage(request.getDosage());
        existingMedicationHistory.setFrequency(request.getFrequency());
        existingMedicationHistory.setStartDate(request.getStartDate());
        existingMedicationHistory.setEndDate(request.getEndDate());
        existingMedicationHistory.setNotes(request.getNotes());

        MedicationHistory updatedMedicationHistory = medicationHistoryRepository.save(existingMedicationHistory);
        return medicationHistoryMapper.toDto(updatedMedicationHistory);
    }

    @Override
    @Transactional
    public void deleteMedicationHistory(UUID patientId, UUID medicationHistoryId) {
        MedicationHistory medicationHistory = medicationHistoryRepository.findByPatientIdAndMedicationHistoryId(patientId, medicationHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MedicationHistoryErrorMessages.MEDICATION_HISTORY_NOT_FOUND, medicationHistoryId, patientId)));

        medicationHistoryRepository.delete(medicationHistory);
    }
}