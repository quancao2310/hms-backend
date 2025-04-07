package com.example.hms.appointmentservice.dtos;

import com.example.hms.models.internal.appointment.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTimeSlotSelectionsResponseDTO {
    private Integer week;
    private DayOfWeek date;
    private List<TimeSlot> availableTimeSlots;
    private List<TimeSlot> unavailableTimeSlots;
}
