package com.example.hms.appointmentservice.dtos;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RemoveDoctorTimeSlotsDTO {
    private List<UUID> timeSlotIds;
    private UUID doctorId;
}
