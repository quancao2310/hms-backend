package com.example.hms.medicalrecordservice.surgicalhistory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurgicalHistoryResponseDTO {
    private UUID id;
    private String name;
    private Integer year;
    private String notes;
}