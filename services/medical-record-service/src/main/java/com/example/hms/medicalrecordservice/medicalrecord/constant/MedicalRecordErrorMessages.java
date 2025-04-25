package com.example.hms.medicalrecordservice.medicalrecord.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MedicalRecordErrorMessages {
    public static final String SYMPTOMS_REQUIRED = "Symptoms are required";
    public static final String DIAGNOSES_REQUIRED = "Diagnoses are required";
    public static final String TREATMENTS_REQUIRED = "Treatments are required";
    public static final String PATIENT_ID_REQUIRED = "Patient ID is required";
    public static final String DOCTOR_ID_REQUIRED = "Doctor ID is required";
    public static final String RECORD_NOT_FOUND = "Medical record not found";
    public static final String INVALID_PATIENT_ID = "Invalid patient ID";
    public static final String INVALID_DOCTOR_ID = "Invalid doctor ID";
}