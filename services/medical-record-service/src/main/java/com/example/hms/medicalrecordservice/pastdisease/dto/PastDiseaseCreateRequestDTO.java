package com.example.hms.medicalrecordservice.pastdisease.dto;

import com.example.hms.medicalrecordservice.pastdisease.constant.PastDiseaseErrorMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PastDiseaseCreateRequestDTO {
    @NotBlank(message = PastDiseaseErrorMessages.NAME_REQUIRED)
    private String name;

    private String notes;
}