package com.example.hms.staffservice.staff.service;

import com.example.hms.models.internal.staff.Staff;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import org.springframework.stereotype.Service;

@Service
public interface StaffMapper {
    StaffDTO toStaffDTO(Staff staff);
}
