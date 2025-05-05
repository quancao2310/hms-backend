package com.example.hms.medicalrecordservice.allergy.service;

import com.example.hms.medicalrecordservice.allergy.dto.AllergyCreateRequestDTO;
import com.example.hms.medicalrecordservice.allergy.dto.AllergyResponseDTO;
import com.example.hms.models.internal.medicalrecord.Allergy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AllergyMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medicalHistory", ignore = true)
    Allergy toEntity(AllergyCreateRequestDTO dto);

    AllergyResponseDTO toDto(Allergy entity);

    List<AllergyResponseDTO> toDtoList(List<Allergy> entities);
}