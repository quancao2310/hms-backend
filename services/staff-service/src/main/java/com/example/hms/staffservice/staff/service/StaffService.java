package com.example.hms.staffservice.staff.service;

import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.common.dto.PageResponse;
import com.example.hms.staffservice.staff.dto.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface StaffService {
    StaffDTO createStaff(CreateStaffDTO dto);
    PageResponse<StaffDTO> getFilteredStaff(StaffFilterDTO filterDTO);
    StaffDTO getStaffById(UUID id);
    StaffDTO updateStaff(UUID id, UpdateStaffDTO dto);
    StaffDTO patchStaff(UUID id, PatchStaffDTO dto);
    MessageDTO updateStaffStatus(UUID id, UpdateStatusDTO dto);
}
