package com.example.hms.medicalrecordservice.medicationhistory.repository;

import com.example.hms.models.internal.medicalrecord.MedicalHistory;
import com.example.hms.models.internal.medicalrecord.MedicationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicationHistoryRepository extends JpaRepository<MedicationHistory, UUID> {
    @Query(value = "SELECT mh FROM MedicationHistory mh " +
            "JOIN mh.medicalHistory med " +
            "JOIN med.patient p " +
            "WHERE p.id = :patientId")
    Page<MedicationHistory> findPageByPatientId(@Param("patientId") UUID patientId, Pageable pageable);

    @Query("SELECT med FROM MedicalHistory med WHERE med.patient.id = :patientId")
    Optional<MedicalHistory> findMedicalHistoryByPatientId(@Param("patientId") UUID patientId);

    @Query("SELECT mh FROM MedicationHistory mh " +
            "JOIN mh.medicalHistory med " +
            "JOIN med.patient p " +
            "WHERE p.id = :patientId AND mh.id = :medicationHistoryId")
    Optional<MedicationHistory> findByPatientIdAndMedicationHistoryId(
            @Param("patientId") UUID patientId,
            @Param("medicationHistoryId") UUID medicationHistoryId);
}