package com.example.hms.staffservice.staff.service.impl;

import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import com.example.hms.staffservice.staff.repository.StaffRepository;
import com.example.hms.staffservice.staff.service.StaffMapper;
import com.example.hms.staffservice.staff.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StaffDTO createStaff(Object dto) {
        return null;
    }

    @Override
    public Object getAllStaff() {
        return null;
    }

    @Override
    public StaffDTO getStaffById(UUID id) {
        return null;
    }

    @Override
    public StaffDTO updateStaff(UUID id, Object dto) {
        return null;
    }

    @Override
    public MessageDTO updateStaffStatus(UUID id, Object dto) {
        return null;
    }
}
