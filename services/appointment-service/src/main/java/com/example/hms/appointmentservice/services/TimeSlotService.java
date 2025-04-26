package com.example.hms.appointmentservice.services;

import com.example.hms.appointmentservice.dtos.CreateBulkTimeSlotRequestDTO;
import com.example.hms.appointmentservice.dtos.CreateTimeSlotRequestDTO;
import com.example.hms.appointmentservice.dtos.GetTimeSlotSelectionsResponseDTO;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

public interface TimeSlotService {
    List<TimeSlot> createBulkTimeSlots(CreateBulkTimeSlotRequestDTO createBulkTimeSlotRequestDTO);
    TimeSlot createTimeSlot(CreateTimeSlotRequestDTO createTimeSlotRequestDTO);
    TimeSlot removeTimeSlot(UUID timeSlotID);
    TimeSlot getTimeSlotById(UUID timeSlotID);
    List<TimeSlot> getTimeSlotsByWeekAndDate(Integer week, DayOfWeek date);
    List<TimeSlot> getTimeSlotsByIds(List<UUID> ids);
    List<TimeSlot> removeTimeSlots(List<UUID> timeSlotIds);
}
