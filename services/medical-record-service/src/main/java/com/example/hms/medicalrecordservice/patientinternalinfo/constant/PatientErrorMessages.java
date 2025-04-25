package com.example.hms.medicalrecordservice.patientinternalinfo.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientErrorMessages {
    public static final String PATIENT_INFORMATION_REQUIRED = "Patient information is required";
    public static final String PATIENT_NOT_FOUND = "Patient not found with ID: %s";
    public static final String PATIENT_SSN_ALREADY_EXISTS = "SSN is already registered";

    public static final String FULL_NAME_REQUIRED = "Full name is required";
    public static final String FULL_NAME_TOO_LONG = "Full name must be less than 100 characters";
    public static final String SSN_REQUIRED = "SSN is required";
    public static final String SSN_INVALID_FORMAT = "SSN must be exactly 12 digits";
    public static final String DOB_REQUIRED = "Date of birth is required";
    public static final String DOB_PAST = "Date of birth must be in the past";
    public static final String SEX_REQUIRED = "Sex is required";
    public static final String NATIONALITY_REQUIRED = "Nationality is required";
    public static final String PHONE_REQUIRED = "Phone number is required";
    public static final String PHONE_INVALID_FORMAT = "Invalid phone number format";
    public static final String ADDRESS_REQUIRED = "Address is required";
    public static final String MARITAL_STATUS_REQUIRED = "Marital status is required";

}