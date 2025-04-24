package com.example.hms.medicalrecordservice.patientrelative.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientRelativeErrorMessages {
    public static final String FULLNAME_REQUIRED = "Relative's full name is required";
    public static final String RELATIONSHIP_REQUIRED = "Relationship is required";
    public static final String PHONE_NUMBER_REQUIRED = "Phone number is required";

    public static final String PATIENT_NOT_FOUND = "Patient not found with ID: %s";
    public static final String PATIENT_RELATIVE_NOT_FOUND = "Patient relative with ID: %s not found for patient with ID: %s";
}