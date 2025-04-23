package com.example.hms.medicalrecordservice.patientinternalinfo.dto;

import com.example.hms.enums.MaritalStatus;
import com.example.hms.enums.Sex;
import com.example.hms.medicalrecordservice.patientinternalinfo.constant.ErrorMessages;
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
public class PatientCreateRequestDTO {

    @NotBlank(message = ErrorMessages.FULL_NAME_REQUIRED)
    @Size(max = 100, message = ErrorMessages.FULL_NAME_TOO_LONG)
    private String fullName;

    @NotBlank(message = ErrorMessages.SSN_REQUIRED)
    @Pattern(regexp = "^\\d{9}$|^\\d{3}-\\d{2}-\\d{4}$", message = ErrorMessages.SSN_INVALID_FORMAT)
    private String ssn;

    @NotNull(message = ErrorMessages.DOB_REQUIRED)
    @Past(message = ErrorMessages.DOB_PAST)
    private LocalDate dateOfBirth;

    @NotNull(message = ErrorMessages.SEX_REQUIRED)
    private Sex sex;

    @NotBlank(message = ErrorMessages.NATIONALITY_REQUIRED)
    private String nationality;

    @NotBlank(message = ErrorMessages.PHONE_REQUIRED)
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = ErrorMessages.PHONE_INVALID_FORMAT)
    private String phoneNumber;

    @NotBlank(message = ErrorMessages.ADDRESS_REQUIRED)
    private String address;

    private String occupation;

    @NotNull(message = ErrorMessages.MARITAL_STATUS_REQUIRED)
    private MaritalStatus maritalStatus;
}