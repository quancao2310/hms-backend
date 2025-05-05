package com.example.hms.medicalrecordservice.allergy.service.impl;

import com.example.hms.medicalrecordservice.allergy.constant.AllergyErrorMessages;
import com.example.hms.medicalrecordservice.allergy.dto.AllergyCreateRequestDTO;
import com.example.hms.medicalrecordservice.allergy.dto.AllergyResponseDTO;
import com.example.hms.medicalrecordservice.allergy.repository.AllergyRepository;
import com.example.hms.medicalrecordservice.allergy.service.AllergyMapper;
import com.example.hms.medicalrecordservice.allergy.service.AllergyService;
import com.example.hms.medicalrecordservice.common.constant.CommonErrorMessages;
import com.example.hms.medicalrecordservice.common.exception.ResourceNotFoundException;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
import com.example.hms.models.internal.medicalrecord.Allergy;
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
public class AllergyServiceImpl implements AllergyService {

    private final AllergyRepository allergyRepository;
    private final AllergyMapper allergyMapper;
    private final PatientService patientService;

    @Override
    @Transactional(readOnly = true)
    public Page<AllergyResponseDTO> getAllergiesPageByPatientId(UUID patientId, int page, int size) {
        patientService.getPatientById(patientId); // Ensure patient exists
        Pageable pageable = PageRequest.of(page, size);
        Page<Allergy> allergyPage = allergyRepository.findPageByPatientId(patientId, pageable);
        return allergyPage.map(allergyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public AllergyResponseDTO getAllergyById(UUID patientId, UUID allergyId) {
        Allergy allergy = allergyRepository.findByPatientIdAndAllergyId(patientId, allergyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(AllergyErrorMessages.ALLERGY_NOT_FOUND, allergyId, patientId)));

        return allergyMapper.toDto(allergy);
    }

    @Override
    @Transactional
    public AllergyResponseDTO createAllergy(UUID patientId, AllergyCreateRequestDTO request) {
        MedicalHistory medicalHistory = allergyRepository.findMedicalHistoryByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(CommonErrorMessages.MEDICAL_HISTORY_NOT_FOUND, patientId)));

        Allergy allergy = allergyMapper.toEntity(request);
        allergy.setMedicalHistory(medicalHistory);

        Allergy savedAllergy = allergyRepository.save(allergy);
        return allergyMapper.toDto(savedAllergy);
    }

    @Override
    @Transactional
    public AllergyResponseDTO updateAllergy(UUID patientId, UUID allergyId, AllergyCreateRequestDTO request) {
        Allergy existingAllergy = allergyRepository.findByPatientIdAndAllergyId(patientId, allergyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(AllergyErrorMessages.ALLERGY_NOT_FOUND, allergyId, patientId)));

        existingAllergy.setAllergen(request.getAllergen());
        existingAllergy.setSeverity(request.getSeverity());
        existingAllergy.setNotes(request.getNotes());

        Allergy updatedAllergy = allergyRepository.save(existingAllergy);
        return allergyMapper.toDto(updatedAllergy);
    }

    @Override
    @Transactional
    public void deleteAllergy(UUID patientId, UUID allergyId) {
        Allergy allergy = allergyRepository.findByPatientIdAndAllergyId(patientId, allergyId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(AllergyErrorMessages.ALLERGY_NOT_FOUND, allergyId, patientId)));

        allergyRepository.delete(allergy);
    }
}