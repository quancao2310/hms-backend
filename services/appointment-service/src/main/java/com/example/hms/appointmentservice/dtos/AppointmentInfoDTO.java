package com.example.hms.appointmentservice.dtos;

import com.example.hms.enums.AppointmentStatus;
import com.example.hms.enums.AppointmentType;
import com.example.hms.models.internal.appointment.Appointment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AppointmentInfoDTO {
    private UUID id;
    private AppointmentType type;
    private AppointmentStatus status;

    private String reason;

    public AppointmentInfoDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.type = appointment.getType();
        this.status = appointment.getStatus();
    }
}
