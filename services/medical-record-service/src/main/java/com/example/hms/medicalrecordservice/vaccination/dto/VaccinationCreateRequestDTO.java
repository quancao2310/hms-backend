package com.example.hms.medicalrecordservice.vaccination.dto;

import com.example.hms.medicalrecordservice.vaccination.constant.VaccinationErrorMessages;
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
public class VaccinationCreateRequestDTO {
    @NotBlank(message = VaccinationErrorMessages.NAME_REQUIRED)
    private String name;

    @NotNull(message = VaccinationErrorMessages.DATE_REQUIRED)
    private LocalDate date;

    private String notes;
}