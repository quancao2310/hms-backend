package com.example.hms.appointmentservice.controllers;

import com.example.hms.appointmentservice.dtos.CreateBulkTimeSlotRequestDTO;
import com.example.hms.appointmentservice.dtos.CreateTimeSlotRequestDTO;
import com.example.hms.appointmentservice.services.AppointmentTimeSlotService;
import com.example.hms.appointmentservice.services.TimeSlotService;
import com.example.hms.models.internal.appointment.TimeSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("timeslots")
@RequiredArgsConstructor
public class TimeSlotController {
    private final TimeSlotService timeSlotService;
    private final AppointmentTimeSlotService appointmentTimeSlotService;

    @PostMapping("/bulk")
    public ResponseEntity<?> createBulkTimeSlots(
            @RequestBody CreateBulkTimeSlotRequestDTO createBulkTimeSlotRequestDTO) {
        List<TimeSlot> timeSlots = timeSlotService.createBulkTimeSlots(createBulkTimeSlotRequestDTO);

        return ResponseEntity.ok(timeSlots);
    }

    @PostMapping()
    public ResponseEntity<?> createTimeSlot(
            @RequestBody CreateTimeSlotRequestDTO createTimeSlotRequestDTO){
        return ResponseEntity.ok(timeSlotService.createTimeSlot(createTimeSlotRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<?> getTimeSlotByWeekAndDate(
            @RequestParam Integer week, @RequestParam DayOfWeek date){
        return ResponseEntity.ok(appointmentTimeSlotService.getTimeSlotsByWeekAndDate(week, date));
    }
}
