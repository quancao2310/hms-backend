package com.example.hms.medicalrecordservice.surgicalhistory.repository;

import com.example.hms.models.internal.medicalrecord.MedicalHistory;
import com.example.hms.models.internal.medicalrecord.SurgicalHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SurgicalHistoryRepository extends JpaRepository<SurgicalHistory, UUID> {
    @Query(value = "SELECT sh FROM SurgicalHistory sh " +
            "JOIN sh.medicalHistory mh " +
            "JOIN mh.patient p " +
            "WHERE p.id = :patientId")
    Page<SurgicalHistory> findPageByPatientId(@Param("patientId") UUID patientId, Pageable pageable);

    @Query("SELECT mh FROM MedicalHistory mh WHERE mh.patient.id = :patientId")
    Optional<MedicalHistory> findMedicalHistoryByPatientId(@Param("patientId") UUID patientId);

    @Query("SELECT sh FROM SurgicalHistory sh " +
            "JOIN sh.medicalHistory mh " +
            "JOIN mh.patient p " +
            "WHERE p.id = :patientId AND sh.id = :surgicalHistoryId")
    Optional<SurgicalHistory> findByPatientIdAndSurgicalHistoryId(
            @Param("patientId") UUID patientId,
            @Param("surgicalHistoryId") UUID surgicalHistoryId);
}