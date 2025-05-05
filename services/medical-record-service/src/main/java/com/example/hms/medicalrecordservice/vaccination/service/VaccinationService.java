package com.example.hms.medicalrecordservice.vaccination.service;

import com.example.hms.medicalrecordservice.vaccination.dto.VaccinationCreateRequestDTO;
import com.example.hms.medicalrecordservice.vaccination.dto.VaccinationResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface VaccinationService {
    Page<VaccinationResponseDTO> getVaccinationsPageByPatientId(UUID patientId, int page, int size);
    VaccinationResponseDTO getVaccinationById(UUID patientId, UUID vaccinationId);
    VaccinationResponseDTO createVaccination(UUID patientId, VaccinationCreateRequestDTO request);
    VaccinationResponseDTO updateVaccination(UUID patientId, UUID vaccinationId, VaccinationCreateRequestDTO request);
    void deleteVaccination(UUID patientId, UUID vaccinationId);
}