package com.example.hms.medicalrecordservice.patientrelative.repository;

import com.example.hms.models.internal.medicalrecord.PatientRelative;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRelativeRepository extends JpaRepository<PatientRelative, UUID> {
    @Query("SELECT pr FROM PatientRelative pr WHERE pr.patient.id = :patientId")
    Page<PatientRelative> findPageByPatientId(@Param("patientId") UUID patientId, Pageable pageable);

    @Query("SELECT pr FROM PatientRelative pr WHERE pr.patient.id = :patientId AND pr.id = :relativeId")
    Optional<PatientRelative> findByPatientIdAndRelativeId(
            @Param("patientId") UUID patientId,
            @Param("relativeId") UUID relativeId);
}