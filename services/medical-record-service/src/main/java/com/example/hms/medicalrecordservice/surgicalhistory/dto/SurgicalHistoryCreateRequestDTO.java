package com.example.hms.medicalrecordservice.surgicalhistory.dto;

import com.example.hms.medicalrecordservice.surgicalhistory.constant.SurgicalHistoryErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurgicalHistoryCreateRequestDTO {
    @NotBlank(message = SurgicalHistoryErrorMessages.NAME_REQUIRED)
    private String name;

    @NotNull(message = SurgicalHistoryErrorMessages.YEAR_REQUIRED)
    private Integer year;

    private String notes;
}