package com.example.hms.patientfacingservice.auth.dtos;

import com.example.hms.patientfacingservice.patientaccount.dtos.PatientAccountDTO;

public record SignInResponseDTO(
        String accessToken,
        PatientAccountDTO userInfo
) {
}
