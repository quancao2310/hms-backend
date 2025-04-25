package com.example.hms.medicalrecordservice.medicalrecord.service.impl;

import com.example.hms.medicalrecordservice.common.exception.ResourceNotFoundException;
import com.example.hms.medicalrecordservice.medicalrecord.constant.MedicalRecordErrorMessages;
import com.example.hms.medicalrecordservice.medicalrecord.dto.MedicalRecordMutationRequestDTO;
import com.example.hms.medicalrecordservice.medicalrecord.dto.MedicalRecordResponseDTO;
import com.example.hms.medicalrecordservice.medicalrecord.repository.DoctorRepository;
import com.example.hms.medicalrecordservice.medicalrecord.repository.MedicalRecordRepository;
import com.example.hms.medicalrecordservice.medicalrecord.service.MedicalRecordMapper;
import com.example.hms.medicalrecordservice.medicalrecord.service.MedicalRecordService;
import com.example.hms.medicalrecordservice.patientinternalinfo.service.PatientService;
import com.example.hms.models.internal.medicalrecord.MedicalRecord;
import com.example.hms.models.internal.medicalrecord.Patient;
import com.example.hms.models.internal.staff.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;
    private final PatientService patientService;
    private final DoctorRepository doctorRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<MedicalRecordResponseDTO> getMedicalRecordsByPatientId(UUID patientId, int page, int size) {
        // Verify patient exists
        patientService.getPatientEntityById(patientId);
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return medicalRecordRepository.findPageByPatientId(patientId, pageable)
                .map(medicalRecordMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicalRecordResponseDTO getMedicalRecordById(UUID patientId, UUID recordId) {
        return medicalRecordRepository.findByPatientIdAndRecordId(patientId, recordId)
                .map(medicalRecordMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalRecordErrorMessages.RECORD_NOT_FOUND));
    }

    @Override
    @Transactional
    public MedicalRecordResponseDTO createMedicalRecord(UUID patientId, MedicalRecordMutationRequestDTO requestDTO) {
        Patient patient = patientService.getPatientEntityById(patientId);
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(MedicalRecordErrorMessages.INVALID_DOCTOR_ID));

        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(requestDTO);

        medicalRecord.setPatient(patient);
        medicalRecord.setDoctor(doctor);

        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);
        return medicalRecordMapper.toResponseDTO(savedMedicalRecord);
    }

    @Override
    @Transactional
    public MedicalRecordResponseDTO updateMedicalRecord(UUID patientId, UUID recordId, MedicalRecordMutationRequestDTO requestDTO) {
        MedicalRecord existingMedicalRecord = medicalRecordRepository.findByPatientIdAndRecordId(patientId, recordId)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalRecordErrorMessages.RECORD_NOT_FOUND));

        Patient patient = patientService.getPatientEntityById(patientId);
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(MedicalRecordErrorMessages.INVALID_DOCTOR_ID));

        MedicalRecord updatedMedicalRecord = medicalRecordMapper.toEntity(requestDTO);

        updatedMedicalRecord.setId(existingMedicalRecord.getId());
        updatedMedicalRecord.setCreatedAt(existingMedicalRecord.getCreatedAt());
        updatedMedicalRecord.setPatient(patient);
        updatedMedicalRecord.setDoctor(doctor);

        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(updatedMedicalRecord);
        return medicalRecordMapper.toResponseDTO(savedMedicalRecord);
    }

    @Override
    @Transactional
    public void deleteMedicalRecord(UUID patientId, UUID recordId) {
        MedicalRecord existingMedicalRecord = medicalRecordRepository.findByPatientIdAndRecordId(patientId, recordId)
                .orElseThrow(() -> new ResourceNotFoundException(MedicalRecordErrorMessages.RECORD_NOT_FOUND));

        medicalRecordRepository.delete(existingMedicalRecord);
    }
}