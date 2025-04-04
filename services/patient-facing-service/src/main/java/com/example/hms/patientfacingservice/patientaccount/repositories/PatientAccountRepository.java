package com.example.hms.patientfacingservice.patientaccount.repositories;

import com.example.hms.models.external.patientaccount.PatientAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface PatientAccountRepository extends JpaRepository<PatientAccount, UUID> {

    Optional<PatientAccount> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsBySsn(String ssn);

    @Modifying
    @Transactional
    @Query("UPDATE PatientAccount p SET p.lastLoginAt = CURRENT_TIMESTAMP WHERE p.id = :id")
    void updateLastLoginAt(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE PatientAccount p SET p.password = :password WHERE p.id = :id")
    void updatePassword(UUID id, String password);
}
