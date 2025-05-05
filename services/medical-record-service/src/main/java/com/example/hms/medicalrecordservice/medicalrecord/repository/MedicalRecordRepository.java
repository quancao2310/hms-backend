package com.example.hms.medicalrecordservice.medicalrecord.repository;

import com.example.hms.models.internal.medicalrecord.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {

    @Query("SELECT mr FROM MedicalRecord mr " +
            "JOIN mr.patient p " +
            "WHERE p.id = :patientId")
    Page<MedicalRecord> findPageByPatientId(@Param("patientId") UUID patientId, Pageable pageable);

    @Query("SELECT mr FROM MedicalRecord mr " +
            "JOIN mr.patient p " +
            "WHERE p.id = :patientId AND mr.id = :recordId")
    Optional<MedicalRecord> findByPatientIdAndRecordId(
            @Param("patientId") UUID patientId,
            @Param("recordId") UUID recordId);
}