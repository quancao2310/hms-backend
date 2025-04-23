package com.example.hms.medicalrecordservice.patientinternalinfo.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {
    public static final String PATIENT_NOT_FOUND = "Patient not found with ID: %s";
    public static final String PATIENT_SSN_ALREADY_EXISTS = "SSN is already registered";

    public static final String INVALID_INPUT = "Invalid input provided";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access to resource";
    public static final String RESOURCE_NOT_FOUND = "Requested resource not found";
    public static final String SERVICE_UNAVAILABLE = "Service temporarily unavailable";

    public static final String MEDICAL_RECORD_NOT_FOUND = "Medical record not found with ID: %s";

    public static final String FULL_NAME_REQUIRED = "Full name is required";
    public static final String FULL_NAME_TOO_LONG = "Full name must be less than 100 characters";
    public static final String SSN_REQUIRED = "SSN is required";
    public static final String SSN_INVALID_FORMAT = "SSN must be in format xxx-xx-xxxx or xxxxxxxxx";
    public static final String DOB_REQUIRED = "Date of birth is required";
    public static final String DOB_PAST = "Date of birth must be in the past";
    public static final String SEX_REQUIRED = "Sex is required";
    public static final String NATIONALITY_REQUIRED = "Nationality is required";
    public static final String PHONE_REQUIRED = "Phone number is required";
    public static final String PHONE_INVALID_FORMAT = "Invalid phone number format";
    public static final String ADDRESS_REQUIRED = "Address is required";
    public static final String MARITAL_STATUS_REQUIRED = "Marital status is required";

}