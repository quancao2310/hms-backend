package com.example.hms.appointmentservice.dtos;

import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDoctorTimeSlotRequestDTO {
    Integer maxAppointments;
    UUID assignedBy;
    TimeSlot timeSlot;
    Doctor doctor;
}
