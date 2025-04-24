package com.example.hms.medicalrecordservice.allergy.repository;

import com.example.hms.models.internal.medicalrecord.Allergy;
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
public interface AllergyRepository extends JpaRepository<Allergy, UUID> {
    @Query(value = "SELECT a FROM Allergy a " +
            "JOIN a.medicalHistory mh " +
            "JOIN mh.patient p " +
            "WHERE p.id = :patientId")
    Page<Allergy> findPageByPatientId(@Param("patientId") UUID patientId, Pageable pageable);

    @Query("SELECT mh FROM MedicalHistory mh WHERE mh.patient.id = :patientId")
    Optional<MedicalHistory> findMedicalHistoryByPatientId(@Param("patientId") UUID patientId);

    @Query("SELECT a FROM Allergy a " +
            "JOIN a.medicalHistory mh " +
            "JOIN mh.patient p " +
            "WHERE p.id = :patientId AND a.id = :allergyId")
    Optional<Allergy> findByPatientIdAndAllergyId(
            @Param("patientId") UUID patientId,
            @Param("allergyId") UUID allergyId);
}