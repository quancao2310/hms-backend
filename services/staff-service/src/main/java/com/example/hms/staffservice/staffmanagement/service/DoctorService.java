package com.example.hms.staffservice.staffmanagement.service;

import com.example.hms.enums.UserRole;
import com.example.hms.models.internal.staff.Doctor;
import com.example.hms.staffservice.common.exception.InvalidOperationException;
import com.example.hms.staffservice.common.exception.ResourceNotFoundException;
import com.example.hms.staffservice.common.service.EmailService;
import com.example.hms.staffservice.common.util.PasswordGenerator;
import com.example.hms.staffservice.staffmanagement.dto.DoctorCreationRequest;
import com.example.hms.staffservice.staffmanagement.dto.DoctorDTO;
import com.example.hms.staffservice.staffmanagement.dto.DoctorUpdateRequest;
import com.example.hms.staffservice.staffmanagement.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final StaffAccountService staffAccountService;
    private final EmailService emailService;
    
    @Transactional(readOnly = true)
    public Page<DoctorDTO> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public DoctorDTO getDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return convertToDto(doctor);
    }
    
    @Transactional(readOnly = true)
    public Page<DoctorDTO> getDoctorsByDepartment(String department, Pageable pageable) {
        return doctorRepository.findByDepartment(department, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Page<DoctorDTO> getDoctorsBySpecialization(String specialization, Pageable pageable) {
        return doctorRepository.findBySpecializationsContaining(specialization, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Page<DoctorDTO> getDoctorsByFullName(String fullName, Pageable pageable) {
        return doctorRepository.findByFullNameContainingIgnoreCase(fullName, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Page<DoctorDTO> getDoctorsWithFilters(String department, String specialization, String fullName, Pageable pageable) {
        // If no filters are provided, return all doctors
        if (department == null && specialization == null && fullName == null) {
            return getAllDoctors(pageable);
        }
        
        // Create a specification that combines all non-null filter criteria
        Specification<Doctor> spec = Specification.where(null);
        
        if (department != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("department"), department));
        }
        
        if (specialization != null) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("specializations"), "%" + specialization + "%"));
        }
        
        if (fullName != null) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%"));
        }
        
        return doctorRepository.findAll(spec, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional
    public DoctorDTO createDoctor(DoctorCreationRequest request) {
        if (request.getRole() != UserRole.DOCTOR) {
            throw new InvalidOperationException("Role must be DOCTOR for doctor creation");
        }
        
        // Generate a secure random password
        String generatedPassword = PasswordGenerator.generateSecurePassword(12);
        
        Doctor doctor = Doctor.builder()
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
                .specializations(request.getSpecializations())
                .licenseNumber(request.getLicenseNumber())
                .qualification(request.getQualification())
                .department(request.getDepartment())
                .build();
        
        Doctor savedDoctor = doctorRepository.save(doctor);
        
        // Send email with the generated password
        emailService.sendPasswordEmail(savedDoctor.getEmail(), savedDoctor.getFullName(), generatedPassword);
        
        return convertToDto(savedDoctor);
    }
    
    @Transactional
    public DoctorDTO updateDoctor(UUID id, DoctorUpdateRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        
        // Update base staff fields using staffAccountService
        staffAccountService.updateStaff(id, request);
        
        // Update doctor-specific fields
        if (request.getSpecializations() != null) doctor.setSpecializations(request.getSpecializations());
        if (request.getLicenseNumber() != null) doctor.setLicenseNumber(request.getLicenseNumber());
        if (request.getQualification() != null) doctor.setQualification(request.getQualification());
        if (request.getDepartment() != null) doctor.setDepartment(request.getDepartment());
        
        return convertToDto(doctorRepository.save(doctor));
    }
    
    @Transactional
    public void deleteDoctor(UUID id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }
    
    private DoctorDTO convertToDto(Doctor doctor) {
        if (doctor == null) return null;
        
        return DoctorDTO.builder()
                .id(doctor.getId())
                .fullName(doctor.getFullName())
                .email(doctor.getEmail())
                .ssn(doctor.getSsn())
                .dateOfBirth(doctor.getDateOfBirth())
                .sex(doctor.getSex())
                .phoneNumber(doctor.getPhoneNumber())
                .nationality(doctor.getNationality())
                .address(doctor.getAddress())
                .role(doctor.getRole())
                .startWorkingDate(doctor.getStartWorkingDate())
                .status(doctor.getStatus())
                .createdAt(doctor.getCreatedAt())
                .lastLoginAt(doctor.getLastLoginAt())
                .specializations(doctor.getSpecializations())
                .licenseNumber(doctor.getLicenseNumber())
                .qualification(doctor.getQualification())
                .department(doctor.getDepartment())
                .build();
    }
}
