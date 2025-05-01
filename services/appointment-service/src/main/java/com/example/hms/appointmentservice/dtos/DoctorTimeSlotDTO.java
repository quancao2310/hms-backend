package com.example.hms.appointmentservice.dtos;

import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Admin;
import com.example.hms.models.internal.staff.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DoctorTimeSlotDTO {
    private UUID id;
    private Integer maxAppointment;
    private TimeSlot timeSlot;
    private Doctor doctor;
    private Admin assignedBy;
    private List<AppointmentInfoDTO> appointmentInfoDTOs;
}
