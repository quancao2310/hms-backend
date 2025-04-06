package com.example.hms.staffservice.staff.service;

import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.common.dto.PageResponse;
import com.example.hms.staffservice.staff.dto.CreateStaffDTO;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import com.example.hms.staffservice.staff.dto.StaffFilterDTO;
import com.example.hms.staffservice.staff.dto.UpdateStaffDTO;
import com.example.hms.staffservice.staff.dto.UpdateStatusDTO;
import com.example.hms.staffservice.staff.dto.PatchStaffDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface StaffService {
    StaffDTO createStaff(CreateStaffDTO dto);
    List<StaffDTO> getAllStaff();
    PageResponse<StaffDTO> getFilteredStaff(StaffFilterDTO filterDTO);
    StaffDTO getStaffById(UUID id);
    StaffDTO updateStaff(UUID id, UpdateStaffDTO dto);
    MessageDTO updateStaffStatus(UUID id, UpdateStatusDTO dto);
    StaffDTO patchStaff(UUID id, PatchStaffDTO patchStaffDTO);
}
