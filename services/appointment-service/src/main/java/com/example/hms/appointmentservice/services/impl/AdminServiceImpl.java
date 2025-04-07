package com.example.hms.appointmentservice.services.impl;

import com.example.hms.appointmentservice.repositories.AdminRepository;
import com.example.hms.appointmentservice.services.AdminService;
import com.example.hms.models.internal.staff.Admin;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Override
    public Admin getAdminById(UUID id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found Admin"));
    }
}
