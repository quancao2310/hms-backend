package com.example.hms.medicalrecordservice.patientinternalinfo.dto;

import com.example.hms.enums.MaritalStatus;
import com.example.hms.enums.Sex;
import com.example.hms.medicalrecordservice.patientinternalinfo.constant.PatientErrorMessages;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientMutationRequestDTO {

    @NotBlank(message = PatientErrorMessages.FULL_NAME_REQUIRED)
    @Size(max = 100, message = PatientErrorMessages.FULL_NAME_TOO_LONG)
    private String fullName;

    @NotBlank(message = PatientErrorMessages.SSN_REQUIRED)
    @Pattern(regexp = "^\\d{12}$", message = PatientErrorMessages.SSN_INVALID_FORMAT)
    private String ssn;

    @NotNull(message = PatientErrorMessages.DOB_REQUIRED)
    @Past(message = PatientErrorMessages.DOB_PAST)
    private LocalDate dateOfBirth;

    @NotNull(message = PatientErrorMessages.SEX_REQUIRED)
    private Sex sex;

    @NotBlank(message = PatientErrorMessages.NATIONALITY_REQUIRED)
    private String nationality;

    @NotBlank(message = PatientErrorMessages.PHONE_REQUIRED)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = PatientErrorMessages.PHONE_INVALID_FORMAT)
    private String phoneNumber;

    @NotBlank(message = PatientErrorMessages.ADDRESS_REQUIRED)
    private String address;

    private String occupation;

    @NotNull(message = PatientErrorMessages.MARITAL_STATUS_REQUIRED)
    private MaritalStatus maritalStatus;
}