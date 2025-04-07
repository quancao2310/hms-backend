package com.example.hms.appointmentservice.repositories;

import com.example.hms.models.internal.staff.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
}
