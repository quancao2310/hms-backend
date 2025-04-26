package com.example.hms.appointmentservice.dtos;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DistributeTimeSlotForDoctorRequestDTO {
    private Integer maxAppointmentsPerTimeSlot;
    private List<UUID> timeSlotIds;
}
