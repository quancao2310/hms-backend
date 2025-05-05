package com.example.hms.medicalrecordservice.pastdisease.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PastDiseaseErrorMessages {
    public static final String NAME_REQUIRED = "Disease name is required";

    public static final String PAST_DISEASE_NOT_FOUND = "Past disease with ID: %s not found for patient with ID: %s";
}