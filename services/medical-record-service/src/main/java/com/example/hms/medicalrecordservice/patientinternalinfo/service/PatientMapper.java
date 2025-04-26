package com.example.hms.medicalrecordservice.patientinternalinfo.service;

import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientCreateRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientMutationRequestDTO;
import com.example.hms.medicalrecordservice.patientinternalinfo.dto.PatientResponseDTO;
import com.example.hms.models.internal.medicalrecord.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PatientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Patient toEntity(PatientMutationRequestDTO request);

    PatientResponseDTO toResponseDTO(Patient patient);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "fullName", source = "patientInfo.fullName")
    @Mapping(target = "ssn", source = "patientInfo.ssn")
    @Mapping(target = "dateOfBirth", source = "patientInfo.dateOfBirth")
    @Mapping(target = "sex", source = "patientInfo.sex")
    @Mapping(target = "nationality", source = "patientInfo.nationality")
    @Mapping(target = "phoneNumber", source = "patientInfo.phoneNumber")
    @Mapping(target = "address", source = "patientInfo.address")
    @Mapping(target = "occupation", source = "patientInfo.occupation")
    @Mapping(target = "maritalStatus", source = "patientInfo.maritalStatus")
    @Mapping(target = "medicalInfo", source = "medicalInfo")
    @Mapping(target = "relatives", ignore = true)
    @Mapping(target = "medicalHistory", ignore = true)
    @Mapping(target = "medicalRecords", ignore = true)
    Patient toEntity(PatientCreateRequestDTO request);
}