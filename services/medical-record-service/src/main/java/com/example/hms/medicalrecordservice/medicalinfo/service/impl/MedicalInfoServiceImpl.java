package com.example.hms.medicalrecordservice.medicalinfo.service.impl;

import com.example.hms.medicalrecordservice.common.exception.ResourceNotFoundException;
import com.example.hms.medicalrecordservice.medicalinfo.constant.MedicalInfoErrorMessages;
import com.example.hms.medicalrecordservice.medicalinfo.dto.MedicalInfoMutationRequestDTO;
import com.example.hms.medicalrecordservice.medicalinfo.dto.MedicalInfoResponseDTO;
import com.example.hms.medicalrecordservice.medicalinfo.repository.MedicalInfoRepository;
import com.example.hms.medicalrecordservice.medicalinfo.service.MedicalInfoMapper;
import com.example.hms.medicalrecordservice.medicalinfo.service.MedicalInfoService;
import com.example.hms.models.internal.medicalrecord.MedicalInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicalInfoServiceImpl implements MedicalInfoService {
    private final MedicalInfoRepository medicalInfoRepository;
    private final MedicalInfoMapper medicalInfoMapper;

    @Override
    @Transactional(readOnly = true)
    public MedicalInfoResponseDTO getPatientMedicalInfo(UUID patientId) {
        MedicalInfo medicalInfo = medicalInfoRepository.findByPatient_Id(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MedicalInfoErrorMessages.MEDICAL_INFO_NOT_FOUND, patientId)));

        return medicalInfoMapper.toResponseDTO(medicalInfo);
    }

    @Override
    @Transactional
    public MedicalInfoResponseDTO updatePatientMedicalInfo(UUID patientId, MedicalInfoMutationRequestDTO request) {
        MedicalInfo medicalInfo = medicalInfoRepository.findByPatient_Id(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(MedicalInfoErrorMessages.MEDICAL_INFO_NOT_FOUND, patientId)));

        medicalInfo.setHeight(request.getHeight());
        medicalInfo.setWeight(request.getWeight());
        medicalInfo.setBloodType(request.getBloodType());
        medicalInfo.setBloodPressure(request.getBloodPressure());

        medicalInfo = medicalInfoRepository.save(medicalInfo);

        return medicalInfoMapper.toResponseDTO(medicalInfo);
    }
}