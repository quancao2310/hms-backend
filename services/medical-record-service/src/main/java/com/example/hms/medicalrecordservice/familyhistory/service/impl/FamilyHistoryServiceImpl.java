package com.example.hms.medicalrecordservice.familyhistory.service.impl;

import com.example.hms.medicalrecordservice.common.constant.CommonErrorMessages;
import com.example.hms.medicalrecordservice.common.exception.ResourceNotFoundException;
import com.example.hms.medicalrecordservice.familyhistory.constant.FamilyHistoryErrorMessages;
import com.example.hms.medicalrecordservice.familyhistory.dto.FamilyHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.familyhistory.dto.FamilyHistoryResponseDTO;
import com.example.hms.medicalrecordservice.familyhistory.repository.FamilyHistoryRepository;
import com.example.hms.medicalrecordservice.familyhistory.service.FamilyHistoryMapper;
import com.example.hms.medicalrecordservice.familyhistory.service.FamilyHistoryService;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
import com.example.hms.models.internal.medicalrecord.FamilyHistory;
import com.example.hms.models.internal.medicalrecord.MedicalHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FamilyHistoryServiceImpl implements FamilyHistoryService {

    private final FamilyHistoryRepository familyHistoryRepository;
    private final FamilyHistoryMapper familyHistoryMapper;
    private final PatientService patientService;

    @Override
    @Transactional(readOnly = true)
    public Page<FamilyHistoryResponseDTO> getFamilyHistoriesPageByPatientId(UUID patientId, int page, int size) {
        patientService.getPatientById(patientId); // Ensure patient exists
        Pageable pageable = PageRequest.of(page, size);
        Page<FamilyHistory> familyHistoryPage = familyHistoryRepository.findPageByPatientId(patientId, pageable);
        return familyHistoryPage.map(familyHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public FamilyHistoryResponseDTO getFamilyHistoryById(UUID patientId, UUID familyHistoryId) {
        FamilyHistory familyHistory = familyHistoryRepository.findByPatientIdAndFamilyHistoryId(patientId, familyHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(FamilyHistoryErrorMessages.FAMILY_HISTORY_NOT_FOUND, familyHistoryId, patientId)));

        return familyHistoryMapper.toDto(familyHistory);
    }

    @Override
    @Transactional
    public FamilyHistoryResponseDTO createFamilyHistory(UUID patientId, FamilyHistoryCreateRequestDTO request) {
        MedicalHistory medicalHistory = familyHistoryRepository.findMedicalHistoryByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(CommonErrorMessages.MEDICAL_HISTORY_NOT_FOUND, patientId)));

        FamilyHistory familyHistory = familyHistoryMapper.toEntity(request);
        familyHistory.setMedicalHistory(medicalHistory);

        FamilyHistory savedFamilyHistory = familyHistoryRepository.save(familyHistory);

        return familyHistoryMapper.toDto(savedFamilyHistory);
    }

    @Override
    @Transactional
    public FamilyHistoryResponseDTO updateFamilyHistory(UUID patientId, UUID familyHistoryId,
                                                        FamilyHistoryCreateRequestDTO request) {
        FamilyHistory existingFamilyHistory = familyHistoryRepository.findByPatientIdAndFamilyHistoryId(patientId, familyHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(FamilyHistoryErrorMessages.FAMILY_HISTORY_NOT_FOUND, familyHistoryId, patientId)));

        existingFamilyHistory.setRelative(request.getRelative());
        existingFamilyHistory.setCondition(request.getCondition());
        existingFamilyHistory.setNotes(request.getNotes());

        FamilyHistory updatedFamilyHistory = familyHistoryRepository.save(existingFamilyHistory);
        return familyHistoryMapper.toDto(updatedFamilyHistory);
    }

    @Override
    @Transactional
    public void deleteFamilyHistory(UUID patientId, UUID familyHistoryId) {
        FamilyHistory familyHistory = familyHistoryRepository.findByPatientIdAndFamilyHistoryId(patientId, familyHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(FamilyHistoryErrorMessages.FAMILY_HISTORY_NOT_FOUND, familyHistoryId, patientId)));

        familyHistoryRepository.delete(familyHistory);
    }
}