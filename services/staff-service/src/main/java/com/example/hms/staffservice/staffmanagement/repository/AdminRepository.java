package com.example.hms.staffservice.staffmanagement.repository;

import com.example.hms.models.internal.staff.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
} 