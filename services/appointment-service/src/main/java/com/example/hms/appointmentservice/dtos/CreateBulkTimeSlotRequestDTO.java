package com.example.hms.appointmentservice.dtos;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Data
public class CreateBulkTimeSlotRequestDTO {
    private Integer week;
    private List<DayOfWeek> dates;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer durationMinutes;
    private Integer maxAppointmentPerTimeSLot;
}
