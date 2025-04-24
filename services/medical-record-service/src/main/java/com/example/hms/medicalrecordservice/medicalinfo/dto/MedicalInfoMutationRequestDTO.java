package com.example.hms.medicalrecordservice.medicalinfo.dto;

import com.example.hms.medicalrecordservice.medicalinfo.constant.MedicalInfoErrorMessages;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalInfoMutationRequestDTO {
    @Min(value = 1, message = MedicalInfoErrorMessages.HEIGHT_NOT_VALID)
    private Integer height; // in cm

    @DecimalMin(value = "0.1", message = MedicalInfoErrorMessages.WEIGHT_NOT_VALID)
    private BigDecimal weight; // in kg

    private String bloodType;
    private String bloodPressure;
}