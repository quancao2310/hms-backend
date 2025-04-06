package com.example.hms.staffservice.staff.repository;

import com.example.hms.models.internal.staff.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, UUID> {
}
