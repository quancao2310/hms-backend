package com.example.hms.medicalrecordservice.pastdisease.service.impl;

import com.example.hms.medicalrecordservice.common.constant.CommonErrorMessages;
import com.example.hms.medicalrecordservice.common.exception.ResourceNotFoundException;
import com.example.hms.medicalrecordservice.pastdisease.constant.PastDiseaseErrorMessages;
import com.example.hms.medicalrecordservice.pastdisease.dto.PastDiseaseCreateRequestDTO;
import com.example.hms.medicalrecordservice.pastdisease.dto.PastDiseaseResponseDTO;
import com.example.hms.medicalrecordservice.pastdisease.repository.PastDiseaseRepository;
import com.example.hms.medicalrecordservice.pastdisease.service.PastDiseaseMapper;
import com.example.hms.medicalrecordservice.pastdisease.service.PastDiseaseService;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
import com.example.hms.models.internal.medicalrecord.MedicalHistory;
import com.example.hms.models.internal.medicalrecord.PastDisease;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PastDiseaseServiceImpl implements PastDiseaseService {

    private final PastDiseaseRepository pastDiseaseRepository;
    private final PastDiseaseMapper pastDiseaseMapper;
    private final PatientService patientService;

    @Override
    @Transactional(readOnly = true)
    public Page<PastDiseaseResponseDTO> getPastDiseasesPageByPatientId(UUID patientId, int page, int size) {
        patientService.getPatientById(patientId); // Ensure patient exists
        Pageable pageable = PageRequest.of(page, size);
        Page<PastDisease> pastDiseasePage = pastDiseaseRepository.findPageByPatientId(patientId, pageable);
        return pastDiseasePage.map(pastDiseaseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PastDiseaseResponseDTO getPastDiseaseById(UUID patientId, UUID pastDiseaseId) {
        PastDisease pastDisease = pastDiseaseRepository.findByPatientIdAndPastDiseaseId(patientId, pastDiseaseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(PastDiseaseErrorMessages.PAST_DISEASE_NOT_FOUND, pastDiseaseId, patientId)));

        return pastDiseaseMapper.toDto(pastDisease);
    }

    @Override
    @Transactional
    public PastDiseaseResponseDTO createPastDisease(UUID patientId, PastDiseaseCreateRequestDTO request) {
        MedicalHistory medicalHistory = pastDiseaseRepository.findMedicalHistoryByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(CommonErrorMessages.MEDICAL_HISTORY_NOT_FOUND, patientId)));

        PastDisease pastDisease = pastDiseaseMapper.toEntity(request);
        pastDisease.setMedicalHistory(medicalHistory);

        PastDisease savedPastDisease = pastDiseaseRepository.save(pastDisease);

        return pastDiseaseMapper.toDto(savedPastDisease);
    }

    @Override
    @Transactional
    public PastDiseaseResponseDTO updatePastDisease(UUID patientId, UUID pastDiseaseId,
                                                    PastDiseaseCreateRequestDTO request) {
        PastDisease existingPastDisease = pastDiseaseRepository.findByPatientIdAndPastDiseaseId(patientId, pastDiseaseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(PastDiseaseErrorMessages.PAST_DISEASE_NOT_FOUND, pastDiseaseId, patientId)));

        existingPastDisease.setName(request.getName());
        existingPastDisease.setNotes(request.getNotes());

        PastDisease updatedPastDisease = pastDiseaseRepository.save(existingPastDisease);
        return pastDiseaseMapper.toDto(updatedPastDisease);
    }

    @Override
    @Transactional
    public void deletePastDisease(UUID patientId, UUID pastDiseaseId) {
        PastDisease pastDisease = pastDiseaseRepository.findByPatientIdAndPastDiseaseId(patientId, pastDiseaseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(PastDiseaseErrorMessages.PAST_DISEASE_NOT_FOUND, pastDiseaseId, patientId)));

        pastDiseaseRepository.delete(pastDisease);
    }
}