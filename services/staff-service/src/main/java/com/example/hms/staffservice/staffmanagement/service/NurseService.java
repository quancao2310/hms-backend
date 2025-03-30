package com.example.hms.staffservice.staffmanagement.service;

import com.example.hms.enums.UserRole;
import com.example.hms.models.internal.staff.Nurse;
import com.example.hms.staffservice.common.exception.InvalidOperationException;
import com.example.hms.staffservice.common.exception.ResourceNotFoundException;
import com.example.hms.staffservice.common.service.EmailService;
import com.example.hms.staffservice.common.util.PasswordGenerator;
import com.example.hms.staffservice.staffmanagement.dto.NurseCreationRequest;
import com.example.hms.staffservice.staffmanagement.dto.NurseDTO;
import com.example.hms.staffservice.staffmanagement.dto.NurseUpdateRequest;
import com.example.hms.staffservice.staffmanagement.repository.NurseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NurseService {

    private final NurseRepository nurseRepository;
    private final StaffAccountService staffAccountService;
    private final EmailService emailService;
    
    @Transactional(readOnly = true)
    public Page<NurseDTO> getAllNurses(Pageable pageable) {
        return nurseRepository.findAll(pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public NurseDTO getNurseById(UUID id) {
        Nurse nurse = nurseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nurse not found with id: " + id));
        return convertToDto(nurse);
    }
    
    @Transactional(readOnly = true)
    public List<NurseDTO> getNursesByQualification(String qualification) {
        return nurseRepository.findByQualificationContaining(qualification).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public NurseDTO createNurse(NurseCreationRequest request) {
        if (request.getRole() != UserRole.NURSE) {
            throw new InvalidOperationException("Role must be NURSE for nurse creation");
        }
        
        // Generate a secure random password
        String generatedPassword = PasswordGenerator.generateSecurePassword(12);
        
        Nurse nurse = Nurse.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(generatedPassword) // Will be encoded in staffAccountService
                .ssn(request.getSsn())
                .dateOfBirth(request.getDateOfBirth())
                .sex(request.getSex())
                .phoneNumber(request.getPhoneNumber())
                .nationality(request.getNationality())
                .address(request.getAddress())
                .role(request.getRole())
                .startWorkingDate(request.getStartWorkingDate())
                .status(request.getStatus())
                .licenseNumber(request.getLicenseNumber())
                .qualification(request.getQualification())
                .build();
        
        Nurse savedNurse = nurseRepository.save(nurse);
        
        // Send email with the generated password
        emailService.sendPasswordEmail(savedNurse.getEmail(), savedNurse.getFullName(), generatedPassword);
        
        return convertToDto(savedNurse);
    }
    
    @Transactional
    public NurseDTO updateNurse(UUID id, NurseUpdateRequest request) {
        Nurse nurse = nurseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nurse not found with id: " + id));
        
        // Update base staff fields using staffAccountService
        staffAccountService.updateStaff(id, request);
        
        // Update nurse-specific fields
        if (request.getLicenseNumber() != null) nurse.setLicenseNumber(request.getLicenseNumber());
        if (request.getQualification() != null) nurse.setQualification(request.getQualification());
        
        return convertToDto(nurseRepository.save(nurse));
    }
    
    @Transactional
    public void deleteNurse(UUID id) {
        if (!nurseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nurse not found with id: " + id);
        }
        nurseRepository.deleteById(id);
    }
    
    protected NurseDTO convertToDto(Nurse nurse) {
        if (nurse == null) return null;
        
        return NurseDTO.builder()
                .id(nurse.getId())
                .fullName(nurse.getFullName())
                .email(nurse.getEmail())
                .ssn(nurse.getSsn())
                .dateOfBirth(nurse.getDateOfBirth())
                .sex(nurse.getSex())
                .phoneNumber(nurse.getPhoneNumber())
                .nationality(nurse.getNationality())
                .address(nurse.getAddress())
                .role(nurse.getRole())
                .startWorkingDate(nurse.getStartWorkingDate())
                .status(nurse.getStatus())
                .createdAt(nurse.getCreatedAt())
                .lastLoginAt(nurse.getLastLoginAt())
                .licenseNumber(nurse.getLicenseNumber())
                .qualification(nurse.getQualification())
                .build();
    }
}
