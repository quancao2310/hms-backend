package com.example.hms.staffservice.staff.repository;

import com.example.hms.models.internal.staff.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
}
