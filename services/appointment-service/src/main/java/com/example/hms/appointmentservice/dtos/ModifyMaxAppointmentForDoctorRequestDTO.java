package com.example.hms.appointmentservice.dtos;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ModifyMaxAppointmentForDoctorRequestDTO {
    private UUID doctorId;
    private List<UUID> doctorTimeSlotIds;
    private Integer maxAppointment;
}
