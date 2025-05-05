package com.example.hms.medicalrecordservice.allergy.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AllergyErrorMessages {
    public static final String ALLERGEN_REQUIRED = "Allergen name is required";
    public static final String SEVERITY_REQUIRED = "Allergy severity is required";

    public static final String ALLERGY_NOT_FOUND = "Allergy with ID: %s not found for patient with ID: %s";
}