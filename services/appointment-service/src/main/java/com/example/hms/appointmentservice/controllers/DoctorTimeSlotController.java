package com.example.hms.appointmentservice.controllers;

import com.example.hms.appointmentservice.dtos.*;
import com.example.hms.appointmentservice.services.DoctorTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("doctor-timeslots")
@RequiredArgsConstructor
public class DoctorTimeSlotController {
    private final DoctorTimeSlotService doctorTimeSlotService;

    @PostMapping()
    public ResponseEntity<?> createDoctorTimeSlot(
            @RequestBody CreateDoctorTimeSlotRequestDTO createDoctorTimeSlotRequestDTO){
        return ResponseEntity.ok(doctorTimeSlotService
                .createDoctorTimeSlot(createDoctorTimeSlotRequestDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerDoctorTimeSlots(
            @RequestBody CreateBulkDoctorTimeSlotRequestDTO createBulkDoctorTimeSlotRequestDTO){
        return ResponseEntity.ok(doctorTimeSlotService
                .registerDoctorTimeSlots(createBulkDoctorTimeSlotRequestDTO));
    }

    @PostMapping("/distribute")
    public ResponseEntity<?> distributeTimeSlotsForDoctors(
            @RequestBody DistributeTimeSlotForDoctorRequestDTO distributeTimeSlotForDoctorRequestDTO){
        return ResponseEntity.ok(doctorTimeSlotService
                .distributeTimeSlotsForDoctors(distributeTimeSlotForDoctorRequestDTO));
    }

    @PostMapping("/modify")
    public ResponseEntity<?> setMaxAppointmentsPerTimeSlotsForDoctor(
            @RequestBody ModifyMaxAppointmentForDoctorRequestDTO modifyMaxAppointmentForDoctorRequestDTO){
        return ResponseEntity.ok(doctorTimeSlotService
                .setMaxAppointmentsPerTimeSlotsForDoctor(modifyMaxAppointmentForDoctorRequestDTO));
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<?> getAvailableDoctorByTimeSlot(@PathVariable UUID id){
        return ResponseEntity.ok(doctorTimeSlotService.getAvailableDoctorByTimeSlot(id));
    }
}
