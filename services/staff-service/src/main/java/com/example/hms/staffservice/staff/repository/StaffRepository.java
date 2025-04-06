package com.example.hms.staffservice.staff.repository;

import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import com.example.hms.models.internal.staff.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff, UUID> {

    Optional<Staff> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsBySsn(String ssn);

    @Query("SELECT s FROM Staff s WHERE " +
           "(:fullName IS NULL OR LOWER(s.fullName) LIKE LOWER(CONCAT('%', CAST(:fullName AS string), '%'))) AND " +
           "(:status IS NULL OR s.status = :status) AND " +
           "(:department IS NULL OR " +
           "(s.role = com.example.hms.enums.UserRole.DOCTOR AND s.id IN (SELECT d.id FROM Doctor d WHERE d.department = :department)) OR " +
           "(s.role = com.example.hms.enums.UserRole.NURSE AND s.id IN (SELECT n.id FROM Nurse n WHERE n.department = :department))) AND " +
           "s.role <> :adminRole")
    Page<Staff> findAllWithFilters(String fullName, WorkingStatus status, String department, UserRole adminRole, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Staff s SET s.lastLoginAt = CURRENT_TIMESTAMP WHERE s.id = :id")
    void updateLastLoginAt(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE Staff s SET s.password = :password WHERE s.id = :id")
    void updatePassword(UUID id, String password);
}
