package com.example.hms.medicalrecordservice.medicalinfo.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MedicalInfoErrorMessages {
    public static final String HEIGHT_NOT_VALID = "Height must be greater than 0";
    public static final String WEIGHT_NOT_VALID = "Weight must be greater than 0";
    public static final String MEDICAL_INFO_NOT_FOUND = "Medical information not found for patient with ID: %s";
}
