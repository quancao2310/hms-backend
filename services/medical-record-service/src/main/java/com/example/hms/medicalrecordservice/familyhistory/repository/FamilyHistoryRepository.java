package com.example.hms.medicalrecordservice.familyhistory.repository;

import com.example.hms.models.internal.medicalrecord.FamilyHistory;
import com.example.hms.models.internal.medicalrecord.MedicalHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FamilyHistoryRepository extends JpaRepository<FamilyHistory, UUID> {
    @Query(value = "SELECT fh FROM FamilyHistory fh " +
            "JOIN fh.medicalHistory mh " +
            "JOIN mh.patient p " +
            "WHERE p.id = :patientId")
    Page<FamilyHistory> findPageByPatientId(@Param("patientId") UUID patientId, Pageable pageable);

    @Query("SELECT mh FROM MedicalHistory mh WHERE mh.patient.id = :patientId")
    Optional<MedicalHistory> findMedicalHistoryByPatientId(@Param("patientId") UUID patientId);

    @Query("SELECT fh FROM FamilyHistory fh " +
            "JOIN fh.medicalHistory mh " +
            "JOIN mh.patient p " +
            "WHERE p.id = :patientId AND fh.id = :familyHistoryId")
    Optional<FamilyHistory> findByPatientIdAndFamilyHistoryId(
            @Param("patientId") UUID patientId,
            @Param("familyHistoryId") UUID familyHistoryId);
}