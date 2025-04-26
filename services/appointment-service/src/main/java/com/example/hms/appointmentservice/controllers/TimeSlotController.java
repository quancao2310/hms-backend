package com.example.hms.appointmentservice.controllers;

import com.example.hms.appointmentservice.dtos.CreateBulkTimeSlotRequestDTO;
import com.example.hms.appointmentservice.dtos.CreateTimeSlotRequestDTO;
import com.example.hms.appointmentservice.dtos.GetTimeSlotSelectionsResponseDTO;
import com.example.hms.appointmentservice.dtos.UUIDsDTO;
import com.example.hms.appointmentservice.services.AppointmentTimeSlotService;
import com.example.hms.appointmentservice.services.TimeSlotService;
import com.example.hms.models.internal.appointment.TimeSlot;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/timeslots")
@RequiredArgsConstructor
public class TimeSlotController {
    private final TimeSlotService timeSlotService;
    private final AppointmentTimeSlotService appointmentTimeSlotService;

    @PostMapping("/bulk")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = TimeSlot.class))
                    )
            )
    })
    public ResponseEntity<?> createBulkTimeSlots(
            @RequestBody CreateBulkTimeSlotRequestDTO createBulkTimeSlotRequestDTO) {
        List<TimeSlot> timeSlots = timeSlotService.createBulkTimeSlots(createBulkTimeSlotRequestDTO);

        return ResponseEntity.ok(timeSlots);
    }

    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TimeSlot.class)
                    )
            )
    })
    public ResponseEntity<?> createTimeSlot(
            @RequestBody CreateTimeSlotRequestDTO createTimeSlotRequestDTO){
        return ResponseEntity.ok(timeSlotService.createTimeSlot(createTimeSlotRequestDTO));
    }

    @GetMapping()
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetTimeSlotSelectionsResponseDTO.class)
                    )
            )
    })
    public ResponseEntity<?> getTimeSlotByWeekAndDate(
            @RequestParam Integer week, @RequestParam DayOfWeek date){
        return ResponseEntity.ok(appointmentTimeSlotService.getTimeSlotsByWeekAndDate(week, date));
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TimeSlot.class)
                    )
            )
    })
    public ResponseEntity<?> deleteTimeSlotById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(appointmentTimeSlotService.removeTimeSlot(id));
    }

    @PostMapping("/delete")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(implementation = TimeSlot.class))
                    )
            )
    })
    public ResponseEntity<?> deleteTimeSlotsById(
            @RequestBody UUIDsDTO ids
            ) {
        return ResponseEntity.ok(appointmentTimeSlotService.removeTimeSlots(ids.getUuids()));
    }
}
