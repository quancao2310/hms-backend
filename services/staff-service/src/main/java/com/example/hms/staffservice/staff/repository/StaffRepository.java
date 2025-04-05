package com.example.hms.staffservice.staff.repository;

import com.example.hms.models.internal.staff.Staff;
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

    @Modifying
    @Transactional
    @Query("UPDATE Staff s SET s.lastLoginAt = CURRENT_TIMESTAMP WHERE s.id = :id")
    void updateLastLoginAt(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE Staff s SET s.password = :password WHERE s.id = :id")
    void updatePassword(UUID id, String password);
}
