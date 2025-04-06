package com.example.hms.staffservice.staff.service.impl;

import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import com.example.hms.models.internal.staff.Doctor;
import com.example.hms.models.internal.staff.Nurse;
import com.example.hms.models.internal.staff.Staff;
import com.example.hms.staffservice.auth.exception.DuplicateEmailException;
import com.example.hms.staffservice.auth.exception.DuplicateSsnException;
import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.common.dto.PageResponse;
import com.example.hms.staffservice.common.service.EmailService;
import com.example.hms.staffservice.staff.dto.CreateStaffDTO;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import com.example.hms.staffservice.staff.dto.UpdateStaffDTO;
import com.example.hms.staffservice.staff.dto.UpdateStatusDTO;
import com.example.hms.staffservice.staff.dto.StaffFilterDTO;
import com.example.hms.staffservice.staff.exception.StaffNotFoundException;
import com.example.hms.staffservice.staff.repository.DoctorRepository;
import com.example.hms.staffservice.staff.repository.NurseRepository;
import com.example.hms.staffservice.staff.repository.StaffRepository;
import com.example.hms.staffservice.staff.service.StaffMapper;
import com.example.hms.staffservice.staff.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;
    private final StaffMapper staffMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
    private static final int PASSWORD_LENGTH = 12;
    private final Random random = new Random();

    private String generateRandomPassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    @Override
    @Transactional
    public StaffDTO createStaff(CreateStaffDTO dto) {
        if (staffRepository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException();
        }

        if (staffRepository.existsBySsn(dto.ssn())) {
            throw new DuplicateSsnException();
        }
        
        // Generate a random password
        String generatedPassword = generateRandomPassword();

        Staff staff;
        
        switch (dto.role()) {
            case DOCTOR -> {
                staff = Doctor.builder()
                        .fullName(dto.fullName())
                        .email(dto.email())
                        .password(passwordEncoder.encode(generatedPassword))
                        .ssn(dto.ssn())
                        .dateOfBirth(dto.dateOfBirth())
                        .sex(dto.sex())
                        .phoneNumber(dto.phoneNumber())
                        .nationality(dto.nationality())
                        .address(dto.address())
                        .biography(dto.biography())
                        .role(dto.role())
                        .startWorkingDate(dto.startWorkingDate())
                        .status(dto.status())
                        .licenseNumber(dto.licenseNumber())
                        .qualification(dto.qualification())
                        .department(dto.department())
                        .specializations(dto.specializations())
                        .services(dto.services())
                        .build();
                
                staff = doctorRepository.save((Doctor) staff);
            }
            case NURSE -> {
                staff = Nurse.builder()
                        .fullName(dto.fullName())
                        .email(dto.email())
                        .password(passwordEncoder.encode(generatedPassword))
                        .ssn(dto.ssn())
                        .dateOfBirth(dto.dateOfBirth())
                        .sex(dto.sex())
                        .phoneNumber(dto.phoneNumber())
                        .nationality(dto.nationality())
                        .address(dto.address())
                        .biography(dto.biography())
                        .role(dto.role())
                        .startWorkingDate(dto.startWorkingDate())
                        .status(dto.status())
                        .licenseNumber(dto.licenseNumber())
                        .qualification(dto.qualification())
                        .department(dto.department())
                        .build();
                
                staff = nurseRepository.save((Nurse) staff);
            }
            default -> throw new IllegalArgumentException("Invalid role");
        }
        
        // Send email with the generated password
        emailService.sendPasswordEmail(staff.getEmail(), staff.getFullName(), generatedPassword);

        return staffMapper.toStaffDTO(staff);
    }

    @Override
    public List<StaffDTO> getAllStaff() {
        return staffRepository.findAll().stream()
                .filter(staff -> staff.getRole() != UserRole.ADMIN)
                .map(staffMapper::toStaffDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StaffDTO getStaffById(UUID id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found with id: " + id));
        
        if (staff.getRole() == UserRole.ADMIN) {
            throw new StaffNotFoundException("Staff not found with id: " + id);
        }
        
        return staffMapper.toStaffDTO(staff);
    }

    @Override
    @Transactional
    public StaffDTO updateStaff(UUID id, UpdateStaffDTO dto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found with id: " + id));
        
        if (staff.getRole() == UserRole.ADMIN) {
            throw new StaffNotFoundException("Staff not found with id: " + id);
        }
        
        if (dto.email() != null && !dto.email().equals(staff.getEmail())) {
            if (staffRepository.existsByEmail(dto.email())) {
                throw new DuplicateEmailException();
            }
            staff.setEmail(dto.email());
        }
        
        if (dto.ssn() != null && !dto.ssn().equals(staff.getSsn())) {
            if (staffRepository.existsBySsn(dto.ssn())) {
                throw new DuplicateSsnException();
            }
            staff.setSsn(dto.ssn());
        }
        
        if (dto.fullName() != null) staff.setFullName(dto.fullName());
        if (dto.dateOfBirth() != null) staff.setDateOfBirth(dto.dateOfBirth());
        if (dto.sex() != null) staff.setSex(dto.sex());
        if (dto.phoneNumber() != null) staff.setPhoneNumber(dto.phoneNumber());
        if (dto.nationality() != null) staff.setNationality(dto.nationality());
        if (dto.address() != null) staff.setAddress(dto.address());
        if (dto.biography() != null) staff.setBiography(dto.biography());
        if (dto.startWorkingDate() != null) staff.setStartWorkingDate(dto.startWorkingDate());
        if (dto.status() != null) staff.setStatus(dto.status());
        
        if (staff.getRole() == UserRole.DOCTOR && staff instanceof Doctor doctor) {
            if (dto.licenseNumber() != null) doctor.setLicenseNumber(dto.licenseNumber());
            if (dto.qualification() != null) doctor.setQualification(dto.qualification());
            if (dto.department() != null) doctor.setDepartment(dto.department());
            if (dto.specializations() != null) doctor.setSpecializations(dto.specializations());
            if (dto.services() != null) doctor.setServices(dto.services());
            
            staff = doctorRepository.save(doctor);
        } else if (staff.getRole() == UserRole.NURSE && staff instanceof Nurse nurse) {
            if (dto.licenseNumber() != null) nurse.setLicenseNumber(dto.licenseNumber());
            if (dto.qualification() != null) nurse.setQualification(dto.qualification());
            if (dto.department() != null) nurse.setDepartment(dto.department());
            
            staff = nurseRepository.save(nurse);
        }
        
        return staffMapper.toStaffDTO(staff);
    }

    @Override
    @Transactional
    public MessageDTO updateStaffStatus(UUID id, UpdateStatusDTO dto) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found with id: " + id));
        
        // Skip admin role
        if (staff.getRole() == UserRole.ADMIN) {
            throw new StaffNotFoundException("Staff not found with id: " + id);
        }
        
        staff.setStatus(dto.status());
        staffRepository.save(staff);
        
        return new MessageDTO("Staff status updated successfully");
    }

    @Override
    public PageResponse<StaffDTO> getFilteredStaff(StaffFilterDTO filterDTO) {
        Sort sort = Sort.by(
                filterDTO.sortDirection().equalsIgnoreCase("asc") ? 
                Sort.Direction.ASC : Sort.Direction.DESC, 
                filterDTO.sortBy()
        );
        
        Pageable pageable = PageRequest.of(
                filterDTO.page(), 
                filterDTO.size(), 
                sort
        );
        
        Page<Staff> staffPage = staffRepository.findAllWithFilters(
                filterDTO.fullName(),
                filterDTO.status(),
                filterDTO.department(),
                UserRole.ADMIN,
                pageable
        );
        
        List<StaffDTO> staffDTOs = staffPage.getContent().stream()
                .map(staffMapper::toStaffDTO)
                .collect(Collectors.toList());
        
        return new PageResponse<>(
                staffDTOs,
                staffPage.getNumber(),
                staffPage.getSize(),
                staffPage.getTotalElements(),
                staffPage.getTotalPages(),
                staffPage.isLast()
        );
    }
}
