package com.example.hms.staffservice.staff.service;

import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface StaffService {
    StaffDTO createStaff(Object dto);
    Object getAllStaff();
    StaffDTO getStaffById(UUID id);
    StaffDTO updateStaff(UUID id, Object dto);
    MessageDTO updateStaffStatus(UUID id, Object dto);
}
