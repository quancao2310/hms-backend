package com.example.hms.staffservice.staff.factory;

import com.example.hms.enums.Sex;
import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import com.example.hms.models.internal.staff.Doctor;
import com.example.hms.models.internal.staff.Staff;
import com.example.hms.staffservice.common.service.EmailService;
import com.example.hms.staffservice.staff.dto.CreateStaffDTO;
import com.example.hms.staffservice.staff.dto.PatchStaffDTO;
import com.example.hms.staffservice.staff.dto.UpdateStaffDTO;
import com.example.hms.staffservice.staff.repository.StaffRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("doctorFactory")
public class DoctorFactory extends StaffFactory {

    public DoctorFactory(PasswordEncoder passwordEncoder, StaffRepository staffRepository, EmailService emailService) {
        super(passwordEncoder, staffRepository, emailService);
    }

    @Override
    protected Staff createStaff(CreateStaffDTO dto, String generatedPassword) {
        return Doctor.builder()
                .fullName(dto.fullName())
                .email(dto.email())
                .password(passwordEncoder.encode(generatedPassword))
                .ssn(dto.ssn())
                .dateOfBirth(dto.dateOfBirth())
                .sex(Sex.valueOf(dto.sex()))
                .phoneNumber(dto.phoneNumber())
                .nationality(dto.nationality())
                .address(dto.address())
                .biography(dto.biography())
                .role(UserRole.valueOf(dto.role()))
                .startWorkingDate(dto.startWorkingDate())
                .status(WorkingStatus.valueOf(dto.status()))
                .licenseNumber(dto.licenseNumber())
                .qualification(dto.qualification())
                .department(dto.department())
                .specializations(dto.specializations())
                .services(dto.services())
                .build();
    }

    @Override
    protected Staff updateStaff(Staff staff, UpdateStaffDTO dto) {
        super.updateStaff(staff, dto);
        if (staff instanceof Doctor doctor) {
            doctor.setLicenseNumber(dto.licenseNumber());
            doctor.setQualification(dto.qualification());
            doctor.setDepartment(dto.department());
            doctor.setSpecializations(dto.specializations());
            doctor.setServices(dto.services());
        }
        return staff;
    }

    @Override
    protected Staff partialUpdateStaff(Staff staff, PatchStaffDTO dto) {
        super.partialUpdateStaff(staff, dto);
        if (staff instanceof Doctor doctor) {
            dto.licenseNumber().ifPresent(doctor::setLicenseNumber);
            dto.qualification().ifPresent(doctor::setQualification);
            dto.department().ifPresent(doctor::setDepartment);
            dto.specializations().ifPresent(doctor::setSpecializations);
            dto.services().ifPresent(doctor::setServices);
        }
        return staff;
    }
}
