package com.example.hms.medicalrecordservice.medicalinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalInfoResponseDTO {
    private UUID id;
    private Integer height;
    private BigDecimal weight;
    private BigDecimal bmi;
    private String bloodType;
    private String bloodPressure;
}