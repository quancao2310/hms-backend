package com.example.hms.staffservice.staffmanagement.service;

import com.example.hms.enums.UserRole;
import com.example.hms.models.internal.staff.Admin;
import com.example.hms.staffservice.common.exception.InvalidOperationException;
import com.example.hms.staffservice.common.exception.ResourceNotFoundException;
import com.example.hms.staffservice.staffmanagement.dto.AdminDTO;
import com.example.hms.staffservice.staffmanagement.dto.AdminStaffCreationRequest;
import com.example.hms.staffservice.staffmanagement.dto.StaffUpdateRequest;
import com.example.hms.staffservice.staffmanagement.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final StaffAccountService staffAccountService;
    
    @Transactional(readOnly = true)
    public Page<AdminDTO> getAllAdmins(Pageable pageable) {
        return adminRepository.findAll(pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public AdminDTO getAdminById(UUID id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
        return convertToDto(admin);
    }
    
    @Transactional
    public AdminDTO createAdmin(AdminStaffCreationRequest request) {
        if (request.getRole() != UserRole.ADMIN) {
            throw new InvalidOperationException("Role must be ADMIN for admin creation");
        }
        
        Admin admin = Admin.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword()) // Will be encoded in staffAccountService
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
        
        return convertToDto(adminRepository.save(admin));
    }
    
    @Transactional
    public AdminDTO updateAdmin(UUID id, StaffUpdateRequest request) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with id: " + id));
        
        // Update base staff fields using staffAccountService
        staffAccountService.updateStaff(id, request);
        
        return convertToDto(adminRepository.findById(id).orElseThrow());
    }
    
    @Transactional
    public void deleteAdmin(UUID id) {
        if (!adminRepository.existsById(id)) {
            throw new ResourceNotFoundException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }
    
    private AdminDTO convertToDto(Admin admin) {
        if (admin == null) return null;
        
        return AdminDTO.builder()
                .id(admin.getId())
                .fullName(admin.getFullName())
                .email(admin.getEmail())
                .ssn(admin.getSsn())
                .dateOfBirth(admin.getDateOfBirth())
                .sex(admin.getSex())
                .phoneNumber(admin.getPhoneNumber())
                .nationality(admin.getNationality())
                .address(admin.getAddress())
                .role(admin.getRole())
                .startWorkingDate(admin.getStartWorkingDate())
                .status(admin.getStatus())
                .createdAt(admin.getCreatedAt())
                .lastLoginAt(admin.getLastLoginAt())
                .build();
    }
} 