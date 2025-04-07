package com.example.hms.appointmentservice.services;

import com.example.hms.models.internal.staff.Doctor;

import java.util.List;
import java.util.UUID;

public interface DoctorService {
    Doctor getDoctorById(UUID id);
    List<Doctor> getAllDoctors();
}
