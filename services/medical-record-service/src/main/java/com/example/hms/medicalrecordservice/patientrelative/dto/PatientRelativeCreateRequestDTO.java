package com.example.hms.medicalrecordservice.patientrelative.dto;

import com.example.hms.medicalrecordservice.patientrelative.constant.PatientRelativeErrorMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRelativeCreateRequestDTO {
    @NotBlank(message = PatientRelativeErrorMessages.FULLNAME_REQUIRED)
    private String fullName;

    @NotBlank(message = PatientRelativeErrorMessages.RELATIONSHIP_REQUIRED)
    private String relationship;

    @NotBlank(message = PatientRelativeErrorMessages.PHONE_NUMBER_REQUIRED)
    private String phoneNumber;
}