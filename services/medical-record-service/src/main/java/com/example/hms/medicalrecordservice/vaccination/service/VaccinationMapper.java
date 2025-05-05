package com.example.hms.medicalrecordservice.vaccination.service;

import com.example.hms.medicalrecordservice.vaccination.dto.VaccinationCreateRequestDTO;
import com.example.hms.medicalrecordservice.vaccination.dto.VaccinationResponseDTO;
import com.example.hms.models.internal.medicalrecord.Vaccination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VaccinationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "medicalHistory", ignore = true)
    Vaccination toEntity(VaccinationCreateRequestDTO dto);

    VaccinationResponseDTO toDto(Vaccination entity);

    List<VaccinationResponseDTO> toDtoList(List<Vaccination> entities);
}