package com.example.hms.medicalrecordservice.pastdisease.repository;

import com.example.hms.models.internal.medicalrecord.MedicalHistory;
import com.example.hms.models.internal.medicalrecord.PastDisease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PastDiseaseRepository extends JpaRepository<PastDisease, UUID> {
    @Query(value = "SELECT pd FROM PastDisease pd " +
            "JOIN pd.medicalHistory mh " +
            "JOIN mh.patient p " +
            "WHERE p.id = :patientId")
    Page<PastDisease> findPageByPatientId(@Param("patientId") UUID patientId, Pageable pageable);

    @Query("SELECT mh FROM MedicalHistory mh WHERE mh.patient.id = :patientId")
    Optional<MedicalHistory> findMedicalHistoryByPatientId(@Param("patientId") UUID patientId);

    @Query("SELECT pd FROM PastDisease pd " +
            "JOIN pd.medicalHistory mh " +
            "JOIN mh.patient p " +
            "WHERE p.id = :patientId AND pd.id = :pastDiseaseId")
    Optional<PastDisease> findByPatientIdAndPastDiseaseId(
            @Param("patientId") UUID patientId,
            @Param("pastDiseaseId") UUID pastDiseaseId);
}