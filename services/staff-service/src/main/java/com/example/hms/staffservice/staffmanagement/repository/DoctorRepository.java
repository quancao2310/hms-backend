package com.example.hms.staffservice.staffmanagement.repository;

import com.example.hms.models.internal.staff.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID>, JpaSpecificationExecutor<Doctor> {
    Page<Doctor> findByDepartment(String department, Pageable pageable);
    Page<Doctor> findBySpecializationsContaining(String specialization, Pageable pageable);
    Page<Doctor> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
}
