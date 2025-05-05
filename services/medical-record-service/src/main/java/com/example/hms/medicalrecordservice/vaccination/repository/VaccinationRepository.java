package com.example.hms.medicalrecordservice.vaccination.repository;

import com.example.hms.models.internal.medicalrecord.MedicalHistory;
import com.example.hms.models.internal.medicalrecord.Vaccination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, UUID> {
    @Query(value = "SELECT v FROM Vaccination v " +
            "JOIN v.medicalHistory mh " +
            "JOIN mh.patient p " +
            "WHERE p.id = :patientId")
    Page<Vaccination> findPageByPatientId(@Param("patientId") UUID patientId, Pageable pageable);

    @Query("SELECT mh FROM MedicalHistory mh WHERE mh.patient.id = :patientId")
    Optional<MedicalHistory> findMedicalHistoryByPatientId(@Param("patientId") UUID patientId);

    @Query("SELECT v FROM Vaccination v " +
            "JOIN v.medicalHistory mh " +
            "JOIN mh.patient p " +
            "WHERE p.id = :patientId AND v.id = :vaccinationId")
    Optional<Vaccination> findByPatientIdAndVaccinationId(
            @Param("patientId") UUID patientId,
            @Param("vaccinationId") UUID vaccinationId);
}