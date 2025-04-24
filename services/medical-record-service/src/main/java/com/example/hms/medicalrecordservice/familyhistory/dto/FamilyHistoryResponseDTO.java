package com.example.hms.medicalrecordservice.familyhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyHistoryResponseDTO {
    private UUID id;
    private String relative;
    private String condition;
    private String notes;
}