package com.example.hms.medicalrecordservice.patientinternalinfo.service.impl;

import com.example.hms.medicalrecordservice.patientinternalinfo.constant.ErrorMessages;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientResponseDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.exception.ResourceNotFoundException;
import com.example.hms.medicalrecordservice.patientinternalinfo.repository.PatientRepository;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientMapper;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
import com.example.hms.models.internal.medicalrecord.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientResponseDTO createPatient(PatientCreateRequestDTO request) {
        if (patientRepository.existsBySsn(request.getSsn())) {
            throw new IllegalArgumentException(ErrorMessages.PATIENT_SSN_ALREADY_EXISTS);
        }

        Patient patient = patientMapper.toEntity(request);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toResponse(savedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponseDTO getPatientById(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.PATIENT_NOT_FOUND, id)));
        return patientMapper.toResponse(patient);
    }
}
