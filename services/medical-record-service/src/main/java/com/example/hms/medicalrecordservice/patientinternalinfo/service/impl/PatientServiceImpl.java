package com.example.hms.medicalrecordservice.patientinternalinfo.service.impl;

import com.example.hms.medicalrecordservice.medicalrecord.service.MedicalRecordMapper;
import com.example.hms.medicalrecordservice.patientinternalinfo.constant.PatientErrorMessages;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientMutationRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientResponseDTO;
import com.example.hms.medicalrecordservice.common.exception.ResourceAlreadyExistsException;
import com.example.hms.medicalrecordservice.common.exception.ResourceNotFoundException;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientWithLatestRecordDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.repository.PatientRepository;
import com.example.hms.medicalrecordservice.medicalinfo.service.MedicalInfoMapper;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientMapper;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
import com.example.hms.models.internal.medicalrecord.MedicalHistory;
import com.example.hms.models.internal.medicalrecord.MedicalInfo;
import com.example.hms.models.internal.medicalrecord.MedicalRecord;
import com.example.hms.models.internal.medicalrecord.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final MedicalInfoMapper medicalInfoMapper;
    private final MedicalRecordMapper medicalRecordMapper;

    @Override
    @Transactional
    public PatientResponseDTO createPatient(PatientCreateRequestDTO request) {
        if (patientRepository.existsBySsn(request.getPatientInfo().getSsn())) {
            throw new ResourceAlreadyExistsException(PatientErrorMessages.PATIENT_SSN_ALREADY_EXISTS);
        }

        Patient patient = patientMapper.toEntity(request);

        MedicalInfo medicalInfo = null;
        if (request.getMedicalInfo() != null) {
            medicalInfo = medicalInfoMapper.toEntity(request.getMedicalInfo());
        } else {
            medicalInfo = new MedicalInfo();
        }

        patient.setMedicalInfo(medicalInfo);
        patient.setMedicalHistory(new MedicalHistory());
        patient.setRelatives(List.of());
        patient.setMedicalRecords(List.of());

        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toResponseDTO(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PatientWithLatestRecordDTO> getPatientsWithLatestMedicalRecord(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Patient> patientsPage = patientRepository.findAllWithMedicalRecords(pageable);

        return patientsPage.map(patient -> {
            PatientWithLatestRecordDTO dto = new PatientWithLatestRecordDTO();

            dto.setPatient(patientMapper.toResponseDTO(patient));

            if (patient.getMedicalRecords() != null && !patient.getMedicalRecords().isEmpty()) {
                patient.getMedicalRecords().stream()
                        .max(Comparator.comparing(MedicalRecord::getCreatedAt))
                        .ifPresent(latestRecord ->
                                dto.setLatestMedicalRecord(medicalRecordMapper.toResponseDTO(latestRecord)));

            }

            return dto;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDTO getPatientById(UUID id) {
        Patient patient = patientRepository.findByIdWithMedicalInfo(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PatientErrorMessages.PATIENT_NOT_FOUND_WITH_ID, id)));
        return patientMapper.toResponseDTO(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDTO getPatientBySsn(String ssn) {
        Patient patient = patientRepository.findBySsnWithMedicalInfo(ssn)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PatientErrorMessages.PATIENT_NOT_FOUND_WITH_SSN, ssn)));
        return patientMapper.toResponseDTO(patient);
    }

    @Override
    @Transactional
    public PatientResponseDTO updatePatient(UUID id, PatientMutationRequestDTO request) {
        Patient existingPatient = patientRepository.findByIdWithMedicalInfo(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(PatientErrorMessages.PATIENT_NOT_FOUND_WITH_ID, id)));

        if (!existingPatient.getSsn().equals(request.getSsn()) &&
                patientRepository.existsBySsn(request.getSsn())) {
            throw new IllegalArgumentException(PatientErrorMessages.PATIENT_SSN_ALREADY_EXISTS);
        }

        existingPatient.setFullName(request.getFullName());
        existingPatient.setSsn(request.getSsn());
        existingPatient.setDateOfBirth(request.getDateOfBirth());
        existingPatient.setSex(request.getSex());
        existingPatient.setNationality(request.getNationality());
        existingPatient.setPhoneNumber(request.getPhoneNumber());
        existingPatient.setAddress(request.getAddress());
        existingPatient.setOccupation(request.getOccupation());
        existingPatient.setMaritalStatus(request.getMaritalStatus());

        Patient savedPatient = patientRepository.save(existingPatient);
        return patientMapper.toResponseDTO(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public Patient getPatientEntityById(UUID id) {
        return patientRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(PatientErrorMessages.PATIENT_NOT_FOUND_WITH_ID, id)));
    }
}
