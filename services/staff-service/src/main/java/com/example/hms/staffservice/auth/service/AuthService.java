package com.example.hms.staffservice.auth.service;

import com.example.hms.staffservice.auth.dto.SignInRequestDTO;
import com.example.hms.staffservice.auth.dto.SignInResponseDTO;
import com.example.hms.staffservice.auth.security.impl.CustomUserDetails;
import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import com.example.hms.staffservice.staff.dto.UpdatePasswordDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    SignInResponseDTO signIn(SignInRequestDTO dto);
    StaffDTO getProfile(CustomUserDetails user);
    MessageDTO changePassword(CustomUserDetails user, UpdatePasswordDTO dto);
}
