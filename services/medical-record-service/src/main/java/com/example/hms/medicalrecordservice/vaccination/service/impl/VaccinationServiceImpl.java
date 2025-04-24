package com.example.hms.medicalrecordservice.vaccination.service.impl;

import com.example.hms.medicalrecordservice.common.constant.CommonErrorMessages;
import com.example.hms.medicalrecordservice.common.exception.ResourceNotFoundException;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
import com.example.hms.medicalrecordservice.vaccination.constant.VaccinationErrorMessages;
import com.example.hms.medicalrecordservice.vaccination.dto.VaccinationCreateRequestDTO;
import com.example.hms.medicalrecordservice.vaccination.dto.VaccinationResponseDTO;
import com.example.hms.medicalrecordservice.vaccination.repository.VaccinationRepository;
import com.example.hms.medicalrecordservice.vaccination.service.VaccinationMapper;
import com.example.hms.medicalrecordservice.vaccination.service.VaccinationService;
import com.example.hms.models.internal.medicalrecord.MedicalHistory;
import com.example.hms.models.internal.medicalrecord.Vaccination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VaccinationServiceImpl implements VaccinationService {

    private final VaccinationRepository vaccinationRepository;
    private final VaccinationMapper vaccinationMapper;
    private final PatientService patientService;

    @Override
    @Transactional(readOnly = true)
    public Page<VaccinationResponseDTO> getVaccinationsPageByPatientId(UUID patientId, int page, int size) {
        patientService.getPatientById(patientId); // Ensure patient exists
        Pageable pageable = PageRequest.of(page, size);
        Page<Vaccination> vaccinationPage = vaccinationRepository.findPageByPatientId(patientId, pageable);
        return vaccinationPage.map(vaccinationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public VaccinationResponseDTO getVaccinationById(UUID patientId, UUID vaccinationId) {
        Vaccination vaccination = vaccinationRepository.findByPatientIdAndVaccinationId(patientId, vaccinationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(VaccinationErrorMessages.VACCINATION_NOT_FOUND, vaccinationId, patientId)));

        return vaccinationMapper.toDto(vaccination);
    }

    @Override
    @Transactional
    public VaccinationResponseDTO createVaccination(UUID patientId, VaccinationCreateRequestDTO request) {
        MedicalHistory medicalHistory = vaccinationRepository.findMedicalHistoryByPatientId(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(CommonErrorMessages.MEDICAL_HISTORY_NOT_FOUND, patientId)));

        Vaccination vaccination = vaccinationMapper.toEntity(request);
        vaccination.setMedicalHistory(medicalHistory);

        Vaccination savedVaccination = vaccinationRepository.save(vaccination);

        return vaccinationMapper.toDto(savedVaccination);
    }

    @Override
    @Transactional
    public VaccinationResponseDTO updateVaccination(UUID patientId, UUID vaccinationId,
                                                    VaccinationCreateRequestDTO request) {
        Vaccination existingVaccination = vaccinationRepository.findByPatientIdAndVaccinationId(patientId, vaccinationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(VaccinationErrorMessages.VACCINATION_NOT_FOUND, vaccinationId, patientId)));

        existingVaccination.setName(request.getName());
        existingVaccination.setDate(request.getDate());
        existingVaccination.setNotes(request.getNotes());

        Vaccination updatedVaccination = vaccinationRepository.save(existingVaccination);
        return vaccinationMapper.toDto(updatedVaccination);
    }

    @Override
    @Transactional
    public void deleteVaccination(UUID patientId, UUID vaccinationId) {
        Vaccination vaccination = vaccinationRepository.findByPatientIdAndVaccinationId(patientId, vaccinationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(VaccinationErrorMessages.VACCINATION_NOT_FOUND, vaccinationId, patientId)));

        vaccinationRepository.delete(vaccination);
    }
}