package com.example.hms.medicalrecordservice.medicationhistory.service;

import com.example.hms.medicalrecordservice.medicationhistory.dto.MedicationHistoryCreateRequestDTO;
import com.example.hms.medicalrecordservice.medicationhistory.dto.MedicationHistoryResponseDTO;
import com.example.hms.models.internal.medicalrecord.MedicationHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MedicationHistoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medicalHistory", ignore = true)
    MedicationHistory toEntity(MedicationHistoryCreateRequestDTO dto);

    MedicationHistoryResponseDTO toDto(MedicationHistory entity);

    List<MedicationHistoryResponseDTO> toDtoList(List<MedicationHistory> entities);
}