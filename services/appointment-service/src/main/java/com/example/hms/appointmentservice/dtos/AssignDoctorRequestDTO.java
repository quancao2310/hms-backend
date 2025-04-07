package com.example.hms.appointmentservice.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class AssignDoctorRequestDTO {
    private UUID appointmentId;
    private UUID doctorId;
}
