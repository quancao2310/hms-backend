package com.example.hms.medicalrecordservice.patientrelative.service.impl;

import com.example.hms.medicalrecordservice.common.exception.ResourceNotFoundException;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
import com.example.hms.medicalrecordservice.patientrelative.constant.PatientRelativeErrorMessages;
import com.example.hms.medicalrecordservice.patientrelative.dto.PatientRelativeCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientrelative.dto.PatientRelativeResponseDTO;
import com.example.hms.medicalrecordservice.patientrelative.repository.PatientRelativeRepository;
import com.example.hms.medicalrecordservice.patientrelative.service.PatientRelativeMapper;
import com.example.hms.medicalrecordservice.patientrelative.service.PatientRelativeService;
import com.example.hms.models.internal.medicalrecord.Patient;
import com.example.hms.models.internal.medicalrecord.PatientRelative;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientRelativeServiceImpl implements PatientRelativeService {

    private final PatientRelativeRepository patientRelativeRepository;
    private final PatientRelativeMapper patientRelativeMapper;
    private final PatientService patientService;

    @Override
    @Transactional(readOnly = true)
    public Page<PatientRelativeResponseDTO> getPatientRelativesPageByPatientId(UUID patientId, int page, int size) {
        // Verify patient exists
        patientService.getPatientEntityById(patientId);

        Pageable pageable = PageRequest.of(page, size);
        Page<PatientRelative> relativesPage = patientRelativeRepository.findPageByPatientId(patientId, pageable);
        return relativesPage.map(patientRelativeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientRelativeResponseDTO getPatientRelativeById(UUID patientId, UUID patientRelativeId) {
        PatientRelative patientRelative = patientRelativeRepository.findByPatientIdAndRelativeId(patientId, patientRelativeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(PatientRelativeErrorMessages.PATIENT_RELATIVE_NOT_FOUND, patientRelativeId, patientId)));

        return patientRelativeMapper.toDto(patientRelative);
    }

    @Override
    @Transactional
    public PatientRelativeResponseDTO createPatientRelative(UUID patientId, PatientRelativeCreateRequestDTO request) {
        // Verify patient exists
        Patient patient = patientService.getPatientEntityById(patientId);

        PatientRelative patientRelative = patientRelativeMapper.toEntity(request);
        patientRelative.setPatient(patient);

        PatientRelative savedRelative = patientRelativeRepository.save(patientRelative);
        return patientRelativeMapper.toDto(savedRelative);
    }

    @Override
    @Transactional
    public PatientRelativeResponseDTO updatePatientRelative(UUID patientId, UUID patientRelativeId,
                                                            PatientRelativeCreateRequestDTO request) {
        PatientRelative existingRelative = patientRelativeRepository.findByPatientIdAndRelativeId(patientId, patientRelativeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(PatientRelativeErrorMessages.PATIENT_RELATIVE_NOT_FOUND, patientRelativeId, patientId)));

        existingRelative.setFullName(request.getFullName());
        existingRelative.setRelationship(request.getRelationship());
        existingRelative.setPhoneNumber(request.getPhoneNumber());

        PatientRelative updatedRelative = patientRelativeRepository.save(existingRelative);
        return patientRelativeMapper.toDto(updatedRelative);
    }

    @Override
    @Transactional
    public void deletePatientRelative(UUID patientId, UUID patientRelativeId) {
        PatientRelative patientRelative = patientRelativeRepository.findByPatientIdAndRelativeId(patientId, patientRelativeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(PatientRelativeErrorMessages.PATIENT_RELATIVE_NOT_FOUND, patientRelativeId, patientId)));

        patientRelativeRepository.delete(patientRelative);
    }
}