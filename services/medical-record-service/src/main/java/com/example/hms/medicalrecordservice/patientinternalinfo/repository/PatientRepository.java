package com.example.hms.medicalrecordservice.patientinternalinfo.repository;

import com.example.hms.models.internal.medicalrecord.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsBySsn(String ssn);
}