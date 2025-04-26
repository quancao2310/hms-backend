package com.example.hms.appointmentservice.dtos;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class CreateTimeSlotRequestDTO {
    private Integer week;
    private DayOfWeek date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer maxAppointmentPerTimeSLot;
}
