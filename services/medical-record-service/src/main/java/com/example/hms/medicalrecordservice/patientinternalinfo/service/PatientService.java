package com.example.hms.medicalrecordservice.patientinternalinfo.service;

import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientResponseDTO;

import java.util.UUID;

public interface PatientService {
    PatientResponseDTO createPatient(PatientCreateRequestDTO request);
    PatientResponseDTO getPatientById(UUID id);
}
