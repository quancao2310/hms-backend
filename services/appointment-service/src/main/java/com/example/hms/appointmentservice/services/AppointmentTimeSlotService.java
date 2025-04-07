package com.example.hms.appointmentservice.services;

import com.example.hms.appointmentservice.dtos.CreateAppointmentRequestDTO;
import com.example.hms.appointmentservice.dtos.GetTimeSlotSelectionsResponseDTO;
import com.example.hms.models.internal.appointment.Appointment;

import java.time.DayOfWeek;
import java.util.UUID;

public interface AppointmentTimeSlotService {
    Appointment rescheduleAppointment(UUID appointmentId, UUID timeSlotId);
    Appointment createAppointment(CreateAppointmentRequestDTO createAppointmentRequestDTO);
    GetTimeSlotSelectionsResponseDTO getTimeSlotsByWeekAndDate(Integer week, DayOfWeek date);
}
