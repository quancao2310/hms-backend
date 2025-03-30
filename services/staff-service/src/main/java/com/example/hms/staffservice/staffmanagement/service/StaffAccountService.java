package com.example.hms.staffservice.staffmanagement.service;

import com.example.hms.enums.UserRole;
import com.example.hms.models.internal.staff.Staff;
import com.example.hms.staffservice.common.exception.ResourceAlreadyExistsException;
import com.example.hms.staffservice.common.exception.ResourceNotFoundException;
import com.example.hms.staffservice.common.util.PasswordGenerator;
import com.example.hms.staffservice.staffmanagement.dto.StaffAccountDTO;
import com.example.hms.staffservice.staffmanagement.dto.StaffCreationRequest;
import com.example.hms.staffservice.staffmanagement.dto.StaffUpdateRequest;
import com.example.hms.staffservice.staffmanagement.repository.StaffAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StaffAccountService {

    private final StaffAccountRepository staffAccountRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Transactional(readOnly = true)
    public Page<StaffAccountDTO> getAllStaffs(Pageable pageable) {
        return staffAccountRepository.findAll(pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public StaffAccountDTO getStaffById(UUID id) {
        Staff staff = staffAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + id));
        return convertToDto(staff);
    }
    
    @Transactional(readOnly = true)
    public StaffAccountDTO getStaffByEmail(String email) {
        Staff staff = staffAccountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with email: " + email));
        return convertToDto(staff);
    }
    
    @Transactional
    public Staff createStaffAccount(StaffCreationRequest request) {
        validateStaffCreation(request);
        
        // If password is null, generate a random one
        String password = request.getPassword();
        if (password == null) {
            password = PasswordGenerator.generateSecurePassword(12);
        }
        
        Staff staff = Staff.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(password))
                .ssn(request.getSsn())
                .dateOfBirth(request.getDateOfBirth())
                .sex(request.getSex())
                .phoneNumber(request.getPhoneNumber())
                .nationality(request.getNationality())
                .address(request.getAddress())
                .role(request.getRole())
                .startWorkingDate(request.getStartWorkingDate())
                .status(request.getStatus())
                .build();
                
        return staffAccountRepository.save(staff);
    }
    
    @Transactional
    public StaffAccountDTO updateStaff(UUID id, StaffUpdateRequest request) {
        Staff staff = staffAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + id));
        
        if (request.getEmail() != null && !request.getEmail().equals(staff.getEmail())) {
            if (staffAccountRepository.existsByEmail(request.getEmail())) {
                throw new ResourceAlreadyExistsException("Email already in use: " + request.getEmail());
            }
            staff.setEmail(request.getEmail());
        }
        
        if (request.getSsn() != null && !request.getSsn().equals(staff.getSsn())) {
            if (staffAccountRepository.existsBySsn(request.getSsn())) {
                throw new ResourceAlreadyExistsException("SSN already in use: " + request.getSsn());
            }
            staff.setSsn(request.getSsn());
        }
        
        if (request.getFullName() != null) staff.setFullName(request.getFullName());
        if (request.getDateOfBirth() != null) staff.setDateOfBirth(request.getDateOfBirth());
        if (request.getSex() != null) staff.setSex(request.getSex());
        if (request.getPhoneNumber() != null) staff.setPhoneNumber(request.getPhoneNumber());
        if (request.getNationality() != null) staff.setNationality(request.getNationality());
        if (request.getAddress() != null) staff.setAddress(request.getAddress());
        if (request.getStartWorkingDate() != null) staff.setStartWorkingDate(request.getStartWorkingDate());
        if (request.getStatus() != null) staff.setStatus(request.getStatus());
        
        Staff updatedStaff = staffAccountRepository.save(staff);
        return convertToDto(updatedStaff);
    }
    
    @Transactional
    public void deleteStaff(UUID id) {
        if (!staffAccountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Staff not found with id: " + id);
        }
        staffAccountRepository.deleteById(id);
    }
    
    @Transactional
    public void updateLastLogin(UUID id) {
        Staff staff = staffAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff not found with id: " + id));
        staff.setLastLoginAt(Instant.now());
        staffAccountRepository.save(staff);
    }
    
    private void validateStaffCreation(StaffCreationRequest request) {
        if (staffAccountRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already in use: " + request.getEmail());
        }
        
        if (request.getSsn() != null && staffAccountRepository.existsBySsn(request.getSsn())) {
            throw new ResourceAlreadyExistsException("SSN already in use: " + request.getSsn());
        }
    }
    
    protected StaffAccountDTO convertToDto(Staff staff) {
        if (staff == null) return null;
        
        return StaffAccountDTO.builder()
                .id(staff.getId())
                .fullName(staff.getFullName())
                .email(staff.getEmail())
                .ssn(staff.getSsn())
                .dateOfBirth(staff.getDateOfBirth())
                .sex(staff.getSex())
                .phoneNumber(staff.getPhoneNumber())
                .nationality(staff.getNationality())
                .address(staff.getAddress())
                .role(staff.getRole())
                .startWorkingDate(staff.getStartWorkingDate())
                .status(staff.getStatus())
                .createdAt(staff.getCreatedAt())
                .lastLoginAt(staff.getLastLoginAt())
                .build();
    }
}
