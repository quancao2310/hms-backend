package com.example.hms.medicalrecordservice.medicationhistory.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MedicationHistoryErrorMessages {
    public static final String NAME_REQUIRED = "Medication name is required";
    public static final String DOSAGE_REQUIRED = "Medication dosage is required";
    public static final String FREQUENCY_REQUIRED = "Medication frequency is required";
    public static final String START_DATE_REQUIRED = "Medication start date is required";

    public static final String MEDICATION_HISTORY_NOT_FOUND = "Medication history with ID: %s not found for patient with ID: %s";
}