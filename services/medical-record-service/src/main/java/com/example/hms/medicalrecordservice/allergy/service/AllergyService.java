package com.example.hms.medicalrecordservice.allergy.service;

import com.example.hms.medicalrecordservice.allergy.dto.AllergyCreateRequestDTO;
import com.example.hms.medicalrecordservice.allergy.dto.AllergyResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AllergyService {
    Page<AllergyResponseDTO> getAllergiesPageByPatientId(UUID patientId, int page, int size);
    AllergyResponseDTO getAllergyById(UUID patientId, UUID allergyId);
    AllergyResponseDTO createAllergy(UUID patientId, AllergyCreateRequestDTO request);
    AllergyResponseDTO updateAllergy(UUID patientId, UUID allergyId, AllergyCreateRequestDTO request);
    void deleteAllergy(UUID patientId, UUID allergyId);
}