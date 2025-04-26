package com.example.hms.appointmentservice.services;

import com.example.hms.models.internal.staff.Admin;

import java.util.UUID;

public interface AdminService {
    Admin getAdminById(UUID id);
}
