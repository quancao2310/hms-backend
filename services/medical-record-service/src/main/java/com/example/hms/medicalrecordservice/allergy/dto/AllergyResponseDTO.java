package com.example.hms.medicalrecordservice.allergy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllergyResponseDTO {
    private UUID id;
    private String allergen;
    private String severity;
    private String notes;
}