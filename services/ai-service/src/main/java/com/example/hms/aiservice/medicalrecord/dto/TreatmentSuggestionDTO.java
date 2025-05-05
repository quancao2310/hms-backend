package com.example.hms.aiservice.medicalrecord.dto;

import dev.langchain4j.model.output.structured.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreatmentSuggestionDTO {
    @Description("Recommended treatments based on the provided symptoms and diagnoses.")
    private String treatments;
    @Description("Additional notes, precautions, or follow-up suggestions related to the treatment.")
    private String notes;
}
