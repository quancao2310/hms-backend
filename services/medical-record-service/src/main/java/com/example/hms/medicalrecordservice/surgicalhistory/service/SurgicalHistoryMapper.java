package com.example.hms.medicalrecordservice.surgicalhistory.service;

import com.example.hms.medicalrecordservice.surgicalhistory.dto.SurgicalHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.surgicalhistory.dto.SurgicalHistoryResponseDTO;
import com.example.hms.models.internal.medicalrecord.SurgicalHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SurgicalHistoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medicalHistory", ignore = true)
    SurgicalHistory toEntity(SurgicalHistoryCreateRequestDTO dto);

    SurgicalHistoryResponseDTO toDto(SurgicalHistory entity);

    List<SurgicalHistoryResponseDTO> toDtoList(List<SurgicalHistory> entities);
}
