package com.example.hms.appointmentservice.dtos;

import com.example.hms.enums.AppointmentType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CreateAppointmentRequestDTO {
    private LocalDate date;
    private AppointmentType type;
    private String reason;
    private List<String> notes;
    private UUID timeSlotId;
    private UUID patientAccountId;
}
