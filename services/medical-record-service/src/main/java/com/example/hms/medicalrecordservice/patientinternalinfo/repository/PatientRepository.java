package com.example.hms.medicalrecordservice.patientinternalinfo.repository;

import com.example.hms.models.internal.medicalrecord.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsBySsn(String ssn);

    @Query("SELECT p FROM Patient p " +
            "LEFT JOIN FETCH p.medicalInfo " +
            "WHERE p.id = :id")
    Optional<Patient> findByIdWithMedicalInfo(UUID id);

    @Query("SELECT p FROM Patient p " +
            "LEFT JOIN FETCH p.medicalInfo " +
            "WHERE p.ssn = :ssn")
    Optional<Patient> findBySsnWithMedicalInfo(String ssn);

    @Query("SELECT DISTINCT p FROM Patient p LEFT JOIN FETCH p.medicalRecords")
    Page<Patient> findAllWithMedicalRecords(Pageable pageable);
}
