package com.example.hms.appointmentservice.repositories;

import com.example.hms.models.internal.appointment.Appointment;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Integer countByTimeSlot(TimeSlot timeSlot);
    Integer countByDoctorAndTimeSlot(Doctor doctor, TimeSlot timeSlot);
}
