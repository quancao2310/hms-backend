package com.example.hms.appointmentservice.controllers;

import com.example.hms.appointmentservice.dtos.AssignDoctorRequestDTO;
import com.example.hms.appointmentservice.dtos.CreateAppointmentRequestDTO;
import com.example.hms.appointmentservice.services.AppointmentService;
import com.example.hms.appointmentservice.services.AppointmentTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentTimeSlotService appointmentTimeSlotService;

    @PostMapping()
    public ResponseEntity<?> createAppointment(
            @RequestBody CreateAppointmentRequestDTO createAppointmentRequestDTO) {
        return ResponseEntity.ok(appointmentTimeSlotService
                .createAppointment(createAppointmentRequestDTO));
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignAppointment(
            @RequestBody AssignDoctorRequestDTO assignDoctorRequestDTO) {
        return ResponseEntity.ok(appointmentService.assignDoctorForAppointment(
                assignDoctorRequestDTO
                        .getAppointmentId(),
                assignDoctorRequestDTO
                        .getDoctorId()));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<?> acceptAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.acceptAppointment(id));
    }

    @GetMapping("/decline/{id}")
    public ResponseEntity<?> declineAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.declineAppointment(id));
    }

    @GetMapping("/complete/{id}")
    public ResponseEntity<?> completeAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.completeAppointment(id));
    }

    @GetMapping("/cancel/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    }

    @GetMapping("/reschedule/{id}")
    public ResponseEntity<?> rescheduleAppointment(@PathVariable UUID id, @RequestParam UUID timeslot) {
        return ResponseEntity.ok(appointmentTimeSlotService
                .rescheduleAppointment(id, timeslot));
    }
}
