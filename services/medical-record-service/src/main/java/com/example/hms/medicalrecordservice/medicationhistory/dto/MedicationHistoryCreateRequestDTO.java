package com.example.hms.medicalrecordservice.medicationhistory.dto;

import com.example.hms.medicalrecordservice.medicationhistory.constant.MedicationHistoryErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicationHistoryCreateRequestDTO {
    @NotBlank(message = MedicationHistoryErrorMessages.NAME_REQUIRED)
    private String name;

    @NotBlank(message = MedicationHistoryErrorMessages.DOSAGE_REQUIRED)
    private String dosage;

    @NotBlank(message = MedicationHistoryErrorMessages.FREQUENCY_REQUIRED)
    private String frequency;

    @NotNull(message = MedicationHistoryErrorMessages.START_DATE_REQUIRED)
    private LocalDate startDate;

    private LocalDate endDate;

    private String notes;
}