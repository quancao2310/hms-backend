package com.example.hms.staffservice.staff.service.impl;

import com.example.hms.staffservice.auth.security.impl.CustomUserDetails;
import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import com.example.hms.staffservice.staff.dto.UpdatePasswordDTO;
import com.example.hms.staffservice.staff.repository.StaffRepository;
import com.example.hms.staffservice.staff.service.StaffAccountService;
import com.example.hms.staffservice.staff.service.StaffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffAccountServiceImpl implements StaffAccountService {

    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StaffDTO getProfile(CustomUserDetails user) {
        return null;
    }

    @Override
    public MessageDTO changePassword(CustomUserDetails user, UpdatePasswordDTO dto) {
        return null;
    }
}
