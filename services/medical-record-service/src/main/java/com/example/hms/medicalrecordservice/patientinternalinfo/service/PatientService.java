package com.example.hms.medicalrecordservice.patientinternalinfo.service;

import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientMutationRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientResponseDTO;
import com.example.hms.models.internal.medicalrecord.Patient;

import java.util.UUID;

public interface PatientService {
    PatientResponseDTO createPatient(PatientCreateRequestDTO request);
    PatientResponseDTO getPatientById(UUID id);
    PatientResponseDTO updatePatient(UUID id, PatientMutationRequestDTO request);

    Patient getPatientEntityById(UUID id);
}
