package com.example.hms.appointmentservice.controllers;

import com.example.hms.appointmentservice.dtos.AssignDoctorRequestDTO;
import com.example.hms.appointmentservice.dtos.CreateAppointmentRequestDTO;
import com.example.hms.appointmentservice.dtos.SearchRequestDTO;
import com.example.hms.appointmentservice.services.AppointmentService;
import com.example.hms.appointmentservice.services.AppointmentTimeSlotService;
import com.example.hms.appointmentservice.utils.SearchParams;
import com.example.hms.models.internal.appointment.Appointment;
import com.example.hms.models.internal.appointment.TimeSlot;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentTimeSlotService appointmentTimeSlotService;

    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Appointment.class)
                    )
            )
    })
    public ResponseEntity<?> createAppointment(
            @RequestBody CreateAppointmentRequestDTO createAppointmentRequestDTO) {
        return ResponseEntity.ok(appointmentTimeSlotService
                .createAppointment(createAppointmentRequestDTO));
    }

    @PostMapping("/assign")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Appointment.class)
                    )
            )
    })
    public ResponseEntity<?> assignAppointment(
            @RequestBody AssignDoctorRequestDTO assignDoctorRequestDTO) {
        return ResponseEntity.ok(appointmentService.assignDoctorForAppointment(
                assignDoctorRequestDTO
                        .getAppointmentId(),
                assignDoctorRequestDTO
                        .getDoctorId()));
    }

//    @GetMapping("{id}")
//    public ResponseEntity<?> getAppointmentById(@PathVariable UUID id) {
//        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
//    }

    @GetMapping()
    public Page<Appointment> searchAppointment(@SearchParams SearchRequestDTO request) {
        return appointmentService.searchAppointments(request);
    }

    @PatchMapping("{id}/accept")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Appointment.class))
                    )
            )
    })
    public ResponseEntity<?> acceptAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.acceptAppointment(id));
    }

    @PatchMapping("{id}/reject")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Appointment.class)
                    )
            )
    })
    public ResponseEntity<?> declineAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.declineAppointment(id));
    }

    @PatchMapping("{id}/complete")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Appointment.class)
                    )
            )
    })
    public ResponseEntity<?> completeAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.completeAppointment(id));
    }

    @PatchMapping("{id}/cancel")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Appointment.class)
                    )
            )
    })
    public ResponseEntity<?> cancelAppointment(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    }

    @PatchMapping("{id}/reschedule")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Appointment.class)
                    )
            )
    })
    public ResponseEntity<?> rescheduleAppointment(@PathVariable UUID id, @RequestParam UUID timeslot) {
        return ResponseEntity.ok(appointmentTimeSlotService
                .rescheduleAppointment(id, timeslot));
    }
}
