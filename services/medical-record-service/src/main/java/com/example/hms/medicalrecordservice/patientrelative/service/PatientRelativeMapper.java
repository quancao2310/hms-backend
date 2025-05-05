package com.example.hms.medicalrecordservice.patientrelative.service;

import com.example.hms.medicalrecordservice.patientrelative.dto.PatientRelativeCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientrelative.dto.PatientRelativeResponseDTO;
import com.example.hms.models.internal.medicalrecord.PatientRelative;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientRelativeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "patient", ignore = true)
    PatientRelative toEntity(PatientRelativeCreateRequestDTO dto);

    PatientRelativeResponseDTO toDto(PatientRelative entity);

    List<PatientRelativeResponseDTO> toDtoList(List<PatientRelative> entities);
}