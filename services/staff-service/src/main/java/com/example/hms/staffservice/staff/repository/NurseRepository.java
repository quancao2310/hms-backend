package com.example.hms.staffservice.staff.repository;

import com.example.hms.models.internal.staff.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NurseRepository extends JpaRepository<Nurse, UUID> {
}
