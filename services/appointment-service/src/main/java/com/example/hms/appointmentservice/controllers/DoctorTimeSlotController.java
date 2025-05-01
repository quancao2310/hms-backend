package com.example.hms.appointmentservice.controllers;

import com.example.hms.appointmentservice.dtos.*;
import com.example.hms.appointmentservice.services.AppointmentTimeSlotService;
import com.example.hms.appointmentservice.services.DoctorTimeSlotService;
import com.example.hms.models.internal.appointment.Appointment;
import com.example.hms.models.internal.appointment.DoctorTimeSlot;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctor-timeslots")
@RequiredArgsConstructor
public class DoctorTimeSlotController {
    private final DoctorTimeSlotService doctorTimeSlotService;
    private final AppointmentTimeSlotService appointmentTimeSlotService;

    @GetMapping("/{doctorId}/timeslots")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = DoctorTimeSlotDTO.class))
                    )
            )
    })
    public ResponseEntity<?> getTimeSlotsByDoctorId(@PathVariable UUID doctorId) {
        return ResponseEntity.ok(doctorTimeSlotService.getTimeSlotsForDoctor(doctorId));
    }

    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = DoctorTimeSlot.class))
                    )
            )
    })
    public ResponseEntity<?> createDoctorTimeSlot(
            @RequestBody CreateDoctorTimeSlotRequestDTO createDoctorTimeSlotRequestDTO){
        return ResponseEntity.ok(doctorTimeSlotService
                .createDoctorTimeSlot(createDoctorTimeSlotRequestDTO));
    }

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = DoctorTimeSlot.class))
                    )
            )
    })
    public ResponseEntity<?> registerDoctorTimeSlots(
            @RequestBody CreateBulkDoctorTimeSlotRequestDTO createBulkDoctorTimeSlotRequestDTO){
        return ResponseEntity.ok(doctorTimeSlotService
                .registerDoctorTimeSlots(createBulkDoctorTimeSlotRequestDTO));
    }

//    @PostMapping("/distribute")
//    public ResponseEntity<?> distributeTimeSlotsForDoctors(
//            @RequestBody DistributeTimeSlotForDoctorRequestDTO distributeTimeSlotForDoctorRequestDTO){
//        return ResponseEntity.ok(doctorTimeSlotService
//                .distributeTimeSlotsForDoctors(distributeTimeSlotForDoctorRequestDTO));
//    }

    @PatchMapping("/modify")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = DoctorTimeSlot.class))
                    )
            )
    })
    public ResponseEntity<?> setMaxAppointmentsPerTimeSlotsForDoctor(
            @RequestBody ModifyMaxAppointmentForDoctorRequestDTO modifyMaxAppointmentForDoctorRequestDTO){
        return ResponseEntity.ok(doctorTimeSlotService
                .setMaxAppointmentsPerTimeSlotsForDoctor(modifyMaxAppointmentForDoctorRequestDTO));
    }



    @GetMapping("/{timeSlotId}/doctors")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = Doctor.class))
                    )
            )
    })
    public ResponseEntity<?> getAvailableDoctorByTimeSlot(@PathVariable UUID timeSlotId){
        return ResponseEntity.ok(doctorTimeSlotService.getAvailableDoctorByTimeSlot(timeSlotId));
    }

    @PostMapping("/delete")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = DoctorTimeSlot.class))
                    )
            )
    })
    public ResponseEntity<?> removeDoctorTimeslots(
            @RequestBody RemoveDoctorTimeSlotsDTO removeDoctorTimeSlotsDTO){
        return ResponseEntity.ok(
                appointmentTimeSlotService.removeDoctorTimeslots(
                        removeDoctorTimeSlotsDTO.getTimeSlotIds(),
                        removeDoctorTimeSlotsDTO.getDoctorId()));
    }
}
