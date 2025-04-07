package com.example.hms.appointmentservice.repositories;

import com.example.hms.models.internal.staff.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
}
