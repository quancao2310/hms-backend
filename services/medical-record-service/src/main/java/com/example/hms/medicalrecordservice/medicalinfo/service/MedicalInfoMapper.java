package com.example.hms.medicalrecordservice.medicalinfo.service;

import com.example.hms.medicalrecordservice.medicalinfo.dto.MedicalInfoMutationRequestDTO;
import com.example.hms.medicalrecordservice.medicalinfo.dto.MedicalInfoResponseDTO;
import com.example.hms.models.internal.medicalrecord.MedicalInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicalInfoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bmi", ignore = true)
    @Mapping(target = "patient", ignore = true)
    MedicalInfo toEntity(MedicalInfoMutationRequestDTO dto);

    MedicalInfoResponseDTO toResponseDTO(MedicalInfo medicalInfo);
}