package com.example.hms.medicalrecordservice.medicalinfo.repository;

import com.example.hms.models.internal.medicalrecord.MedicalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MedicalInfoRepository extends JpaRepository<MedicalInfo, UUID> {
    Optional<MedicalInfo> findByPatient_Id(UUID patientId);
}
