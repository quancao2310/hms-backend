package com.example.hms.medicalrecordservice.allergy.dto;

import com.example.hms.medicalrecordservice.allergy.constant.AllergyErrorMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllergyCreateRequestDTO {
    @NotBlank(message = AllergyErrorMessages.ALLERGEN_REQUIRED)
    private String allergen;

    @NotBlank(message = AllergyErrorMessages.SEVERITY_REQUIRED)
    private String severity;

    private String notes;
}