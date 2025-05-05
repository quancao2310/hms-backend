package com.example.hms.medicalrecordservice.familyhistory.dto;

import com.example.hms.medicalrecordservice.familyhistory.constant.FamilyHistoryErrorMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyHistoryCreateRequestDTO {
    @NotBlank(message = FamilyHistoryErrorMessages.RELATIVE_REQUIRED)
    private String relative;

    @NotBlank(message = FamilyHistoryErrorMessages.CONDITION_REQUIRED)
    private String condition;

    private String notes;
}