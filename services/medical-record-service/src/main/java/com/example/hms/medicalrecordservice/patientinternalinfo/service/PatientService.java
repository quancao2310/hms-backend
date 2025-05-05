package com.example.hms.medicalrecordservice.patientinternalinfo.service;

import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientMutationRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientResponseDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientWithLatestRecordDTO;
import com.example.hms.models.internal.medicalrecord.Patient;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PatientService {
    PatientResponseDTO createPatient(PatientCreateRequestDTO request);
    Page<PatientWithLatestRecordDTO> getPatientsWithLatestMedicalRecord(int page, int size);
    PatientResponseDTO getPatientById(UUID id);
    PatientResponseDTO getPatientBySsn(String ssn);
    PatientResponseDTO updatePatient(UUID id, PatientMutationRequestDTO request);

    Patient getPatientEntityById(UUID id);
}
