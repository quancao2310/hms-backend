package com.example.hms.staffservice.staffmanagement.repository;

import com.example.hms.models.internal.staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StaffAccountRepository extends JpaRepository<Staff, UUID> {
    Optional<Staff> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsBySsn(String ssn);
}
