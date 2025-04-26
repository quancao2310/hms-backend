package com.example.hms.appointmentservice.services;

import com.example.hms.appointmentservice.dtos.SearchRequestDTO;
import com.example.hms.enums.AppointmentStatus;
import com.example.hms.models.internal.appointment.Appointment;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;
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
    Integer countAppointmentByDoctorAndTimeSlotAndStatus(
            Doctor doctor, TimeSlot timeSlot, AppointmentStatus appointmentStatus);
    Integer countAppointmentByTimeSlotAndStatusIn(
            TimeSlot timeSlot, List<AppointmentStatus> appointmentStatuses
    );
    Page<Appointment> searchAppointments(SearchRequestDTO requestDTO);
    Appointment saveAppointment(Appointment appointment);
    List<Appointment> getAppointmentByTimeSlot(TimeSlot timeSlot);
    List<Appointment> getAppointmentByTimeSlots(List<TimeSlot> timeSlots);
}
