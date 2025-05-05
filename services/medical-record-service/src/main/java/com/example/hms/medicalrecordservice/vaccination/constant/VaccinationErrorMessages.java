package com.example.hms.medicalrecordservice.vaccination.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VaccinationErrorMessages {
    public static final String NAME_REQUIRED = "Vaccination name is required";
    public static final String DATE_REQUIRED = "Vaccination date is required";

    public static final String VACCINATION_NOT_FOUND = "Vaccination with ID: %s not found for patient with ID: %s";
}