package com.example.hms.appointmentservice.dtos;

import lombok.Data;

import java.util.UUID;
import java.util.List;

@Data
public class CreateBulkDoctorTimeSlotRequestDTO {
    UUID doctorId;
    List<UUID> timeSlotIds;
    Integer maxAppointmentsPerTimeSlot;
}
