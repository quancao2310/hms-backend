package com.example.hms.medicalrecordservice.medicalrecord.service;

import com.example.hms.medicalrecordservice.medicalrecord.dto.MedicalRecordMutationRequestDTO;
import com.example.hms.medicalrecordservice.medicalrecord.dto.MedicalRecordResponseDTO;
import com.example.hms.models.internal.medicalrecord.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicalRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    MedicalRecord toEntity(MedicalRecordMutationRequestDTO request);

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientName", source = "patient.fullName")
    @Mapping(target = "doctorId", source = "doctor.id")
    @Mapping(target = "doctorName", source = "doctor.fullName")
    MedicalRecordResponseDTO toResponseDTO(MedicalRecord medicalRecord);
}