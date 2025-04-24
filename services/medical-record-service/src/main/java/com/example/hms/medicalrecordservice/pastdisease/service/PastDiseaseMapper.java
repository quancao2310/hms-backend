package com.example.hms.medicalrecordservice.pastdisease.service;

import com.example.hms.medicalrecordservice.pastdisease.dto.PastDiseaseCreateRequestDTO;
import com.example.hms.medicalrecordservice.pastdisease.dto.PastDiseaseResponseDTO;
import com.example.hms.models.internal.medicalrecord.PastDisease;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PastDiseaseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medicalHistory", ignore = true)
    PastDisease toEntity(PastDiseaseCreateRequestDTO dto);

    PastDiseaseResponseDTO toDto(PastDisease entity);

    List<PastDiseaseResponseDTO> toDtoList(List<PastDisease> entities);
}