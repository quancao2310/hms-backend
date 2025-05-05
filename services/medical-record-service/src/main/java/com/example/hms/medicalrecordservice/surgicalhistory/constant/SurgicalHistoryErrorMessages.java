package com.example.hms.medicalrecordservice.surgicalhistory.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SurgicalHistoryErrorMessages {
    public static final String NAME_REQUIRED = "Surgery name is required";
    public static final String YEAR_REQUIRED = "Year is required";

    public static final String SURGICAL_HISTORY_NOT_FOUND = "Surgical history with ID: %s not found for patient with ID: %s";
}