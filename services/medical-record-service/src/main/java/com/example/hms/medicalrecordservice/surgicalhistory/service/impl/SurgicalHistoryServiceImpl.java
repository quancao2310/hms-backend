package com.example.hms.medicalrecordservice.surgicalhistory.service.impl;

import com.example.hms.medicalrecordservice.common.constant.CommonErrorMessages;
import com.example.hms.medicalrecordservice.common.exception.ResourceNotFoundException;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
import com.example.hms.medicalrecordservice.surgicalhistory.constant.SurgicalHistoryErrorMessages;
import com.example.hms.medicalrecordservice.surgicalhistory.dto.SurgicalHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.surgicalhistory.dto.SurgicalHistoryResponseDTO;
import com.example.hms.medicalrecordservice.surgicalhistory.repository.SurgicalHistoryRepository;
import com.example.hms.medicalrecordservice.surgicalhistory.service.SurgicalHistoryMapper;
import com.example.hms.medicalrecordservice.surgicalhistory.service.SurgicalHistoryService;
import com.example.hms.models.internal.medicalrecord.MedicalHistory;
import com.example.hms.models.internal.medicalrecord.SurgicalHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurgicalHistoryServiceImpl implements SurgicalHistoryService {

    private final SurgicalHistoryRepository surgicalHistoryRepository;
    private final SurgicalHistoryMapper surgicalHistoryMapper;
    private final PatientService patientService;

    @Override
    @Transactional(readOnly = true)
    public Page<SurgicalHistoryResponseDTO> getSurgicalHistoriesPageByPatientId(UUID patientId, int page, int size) {
        patientService.getPatientById(patientId); // Ensure patient exists
        Pageable pageable = PageRequest.of(page, size);
        Page<SurgicalHistory> surgicalHistoryPage = surgicalHistoryRepository.findPageByPatientId(patientId, pageable);
        return surgicalHistoryPage.map(surgicalHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public SurgicalHistoryResponseDTO getSurgicalHistoryById(UUID patientId, UUID surgicalHistoryId) {
        SurgicalHistory surgicalHistory = surgicalHistoryRepository.findByPatientIdAndSurgicalHistoryId(patientId, surgicalHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(SurgicalHistoryErrorMessages.SURGICAL_HISTORY_NOT_FOUND, surgicalHistoryId, patientId)));

        return surgicalHistoryMapper.toDto(surgicalHistory);
    }

    @Override
    @Transactional
    public SurgicalHistoryResponseDTO createSurgicalHistory(UUID patientId, SurgicalHistoryCreateRequestDTO request) {
        MedicalHistory medicalHistory = surgicalHistoryRepository.findMedicalHistoryByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(CommonErrorMessages.MEDICAL_HISTORY_NOT_FOUND, patientId)));

        SurgicalHistory surgicalHistory = surgicalHistoryMapper.toEntity(request);
        surgicalHistory.setMedicalHistory(medicalHistory);

        SurgicalHistory savedSurgicalHistory = surgicalHistoryRepository.save(surgicalHistory);

        return surgicalHistoryMapper.toDto(savedSurgicalHistory);
    }

    @Override
    @Transactional
    public SurgicalHistoryResponseDTO updateSurgicalHistory(UUID patientId, UUID surgicalHistoryId,
                                                            SurgicalHistoryCreateRequestDTO request) {
        SurgicalHistory existingSurgicalHistory = surgicalHistoryRepository.findByPatientIdAndSurgicalHistoryId(patientId, surgicalHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(SurgicalHistoryErrorMessages.SURGICAL_HISTORY_NOT_FOUND, surgicalHistoryId, patientId)));

        existingSurgicalHistory.setName(request.getName());
        existingSurgicalHistory.setYear(request.getYear());
        existingSurgicalHistory.setNotes(request.getNotes());

        SurgicalHistory updatedSurgicalHistory = surgicalHistoryRepository.save(existingSurgicalHistory);
        return surgicalHistoryMapper.toDto(updatedSurgicalHistory);
    }

    @Override
    @Transactional
    public void deleteSurgicalHistory(UUID patientId, UUID surgicalHistoryId) {
        SurgicalHistory surgicalHistory = surgicalHistoryRepository.findByPatientIdAndSurgicalHistoryId(patientId, surgicalHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(SurgicalHistoryErrorMessages.SURGICAL_HISTORY_NOT_FOUND, surgicalHistoryId, patientId)));

        surgicalHistoryRepository.delete(surgicalHistory);
    }
}