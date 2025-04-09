package com.example.hms.staffservice.staff.dto;

import com.example.hms.enums.Sex;
import com.example.hms.enums.WorkingStatus;
import com.example.hms.staffservice.common.validator.annotation.ValidEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public record PatchStaffDTO(
        Optional<
                @NotBlank(message = "Full name is required") String> fullName,

        Optional<
                @NotBlank(message = "Email is required")
                @Email(message = "Email must be valid")
                        String> email,

        Optional<
                @NotBlank(message = "SSN is required") String> ssn,

        Optional<
                @Past(message = "Date of birth must be in the past") LocalDate> dateOfBirth,

        Optional<
                @ValidEnum(enumClass = Sex.class) String> sex,

        Optional<
                @NotBlank(message = "Phone number is required") String> phoneNumber,

        Optional<
                @NotBlank(message = "Nationality is required") String> nationality,

        Optional<
                @NotBlank(message = "Address is required") String> address,

        Optional<String> biography,

        Optional<LocalDate> startWorkingDate,

        Optional<
                @ValidEnum(enumClass = WorkingStatus.class) String> status,

        // Fields for Doctor and Nurse
        Optional<String> licenseNumber,
        Optional<String> qualification,
        Optional<String> department,

        // Fields for Doctor only
        Optional<Set<String>> specializations,
        Optional<Set<String>> services
) {
    public boolean isEmpty() {
        return fullName.isEmpty() &&
                email.isEmpty() &&
                ssn.isEmpty() &&
                dateOfBirth.isEmpty() &&
                sex.isEmpty() &&
                phoneNumber.isEmpty() &&
                nationality.isEmpty() &&
                address.isEmpty() &&
                biography.isEmpty() &&
                startWorkingDate.isEmpty() &&
                status.isEmpty() &&
                licenseNumber.isEmpty() &&
                qualification.isEmpty() &&
                department.isEmpty() &&
                specializations.isEmpty() &&
                services.isEmpty();
    }
}
