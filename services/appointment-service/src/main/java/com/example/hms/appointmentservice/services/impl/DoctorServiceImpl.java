package com.example.hms.appointmentservice.services.impl;

import com.example.hms.appointmentservice.repositories.DoctorRepository;
import com.example.hms.appointmentservice.services.DoctorService;
import com.example.hms.models.internal.staff.Doctor;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;

    @Override
    public Doctor getDoctorById(UUID id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found"));
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public List<Doctor> getDoctorsByIds(List<UUID> ids) {
        return doctorRepository.findAllById(ids);
    }
}
