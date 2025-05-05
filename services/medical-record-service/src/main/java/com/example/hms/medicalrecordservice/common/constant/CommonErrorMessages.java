package com.example.hms.medicalrecordservice.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonErrorMessages {
    public static final String INVALID_INPUT = "Invalid input provided";
    public static final String MEDICAL_HISTORY_NOT_FOUND = "Medical history not found for patient with ID: %s";
}
