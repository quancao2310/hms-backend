package com.example.hms.aiservice.patientfacingcommunication.repository;

import com.example.hms.models.internal.ai.ChatMemoryPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatMemoryPersistenceRepository extends JpaRepository<ChatMemoryPersistence, UUID> {
    Optional<ChatMemoryPersistence> findByPatientId(UUID patientId);
}
