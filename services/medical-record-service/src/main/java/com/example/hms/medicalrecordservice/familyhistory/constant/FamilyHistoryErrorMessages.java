package com.example.hms.medicalrecordservice.familyhistory.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FamilyHistoryErrorMessages {
    public static final String RELATIVE_REQUIRED = "Relative information is required";
    public static final String CONDITION_REQUIRED = "Medical condition is required";

    public static final String FAMILY_HISTORY_NOT_FOUND = "Family history with ID: %s not found for patient with ID: %s";
}