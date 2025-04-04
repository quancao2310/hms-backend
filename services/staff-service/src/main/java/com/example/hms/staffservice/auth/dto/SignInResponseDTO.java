package com.example.hms.staffservice.auth.dto;

import com.example.hms.staffservice.staff.dto.StaffDTO;

public record SignInResponseDTO(
        String accessToken,
        StaffDTO userInfo
) {
}
