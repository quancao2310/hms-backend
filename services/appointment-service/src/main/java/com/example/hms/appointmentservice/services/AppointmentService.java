package com.example.hms.appointmentservice.services;

import com.example.hms.appointmentservice.dtos.CreateAppointmentRequestDTO;
import com.example.hms.models.internal.appointment.Appointment;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;

import java.util.UUID;

public interface AppointmentService {
    Appointment assignDoctorForAppointment(UUID appointmentId, UUID doctorId);
    Appointment getAppointmentById(UUID id);
    Appointment acceptAppointment(UUID id);
    Appointment declineAppointment(UUID id);
    Appointment completeAppointment(UUID id);
    Appointment cancelAppointment(UUID id);
    Integer countAppointmentByTimeSlot(TimeSlot timeSlot);
    Integer countAppointmentByDoctorAndTimeSlot(Doctor doctor, TimeSlot timeSlot);
    Appointment saveAppointment(Appointment appointment);
}
