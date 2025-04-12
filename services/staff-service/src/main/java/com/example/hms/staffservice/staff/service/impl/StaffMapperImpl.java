package com.example.hms.staffservice.staff.service.impl;

import com.example.hms.models.internal.staff.Doctor;
import com.example.hms.models.internal.staff.Nurse;
import com.example.hms.models.internal.staff.Staff;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import com.example.hms.staffservice.staff.service.StaffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffMapperImpl implements StaffMapper {

    @Override
    public StaffDTO toStaffDTO(Staff staff) {
        if (staff == null) {
            return null;
        }

        StaffDTO.StaffDTOBuilder builder = StaffDTO.builder()
                .id(staff.getId())
                .fullName(staff.getFullName())
                .email(staff.getEmail())
                .ssn(staff.getSsn())
                .dateOfBirth(staff.getDateOfBirth())
                .sex(staff.getSex())
                .phoneNumber(staff.getPhoneNumber())
                .nationality(staff.getNationality())
                .address(staff.getAddress())
                .biography(staff.getBiography())
                .role(staff.getRole())
                .startWorkingDate(staff.getStartWorkingDate())
                .status(staff.getStatus())
                .createdAt(staff.getCreatedAt())
                .lastLoginAt(staff.getLastLoginAt());

        if (staff instanceof Doctor doctor) {
            builder.specializations(doctor.getSpecializations())
                    .services(doctor.getServices())
                    .licenseNumber(doctor.getLicenseNumber())
                    .qualification(doctor.getQualification())
                    .department(doctor.getDepartment());
        }

        if (staff instanceof Nurse nurse) {
            builder.licenseNumber(nurse.getLicenseNumber())
                    .qualification(nurse.getQualification())
                    .department(nurse.getDepartment());
        }

        return builder.build();
    }
}
