package com.example.hms.medicalrecordservice.vaccination.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationResponseDTO {
    private UUID id;
    private String name;
    private LocalDate date;
    private String notes;
}