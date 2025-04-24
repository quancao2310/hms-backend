package com.example.hms.medicalrecordservice.familyhistory.service;

import com.example.hms.medicalrecordservice.familyhistory.dto.FamilyHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.familyhistory.dto.FamilyHistoryResponseDTO;
import com.example.hms.models.internal.medicalrecord.FamilyHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FamilyHistoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medicalHistory", ignore = true)
    FamilyHistory toEntity(FamilyHistoryCreateRequestDTO dto);

    FamilyHistoryResponseDTO toDto(FamilyHistory entity);

    List<FamilyHistoryResponseDTO> toDtoList(List<FamilyHistory> entities);
}