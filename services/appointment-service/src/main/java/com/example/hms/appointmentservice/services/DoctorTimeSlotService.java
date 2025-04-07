package com.example.hms.appointmentservice.services;

import com.example.hms.appointmentservice.dtos.CreateDoctorTimeSlotRequestDTO;
import com.example.hms.appointmentservice.dtos.CreateBulkDoctorTimeSlotRequestDTO;
import com.example.hms.appointmentservice.dtos.ModifyMaxAppointmentForDoctorRequestDTO;
import com.example.hms.appointmentservice.dtos.DistributeTimeSlotForDoctorRequestDTO;
import com.example.hms.models.internal.appointment.DoctorTimeSlot;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;

import java.util.List;
import java.util.UUID;

public interface DoctorTimeSlotService {
    DoctorTimeSlot createDoctorTimeSlot(CreateDoctorTimeSlotRequestDTO createDoctorTimeSlotRequestDTO);
    List<DoctorTimeSlot> registerDoctorTimeSlots(CreateBulkDoctorTimeSlotRequestDTO createBulkDoctorTimeSlotRequestDTO);
    List<DoctorTimeSlot> distributeTimeSlotsForDoctors(DistributeTimeSlotForDoctorRequestDTO distributeTimeSlotForDoctorRequestDTO);
    List<DoctorTimeSlot> setMaxAppointmentsPerTimeSlotsForDoctor(ModifyMaxAppointmentForDoctorRequestDTO modifyMaxAppointmentForDoctorRequestDTO);
    List<Doctor> getAvailableDoctorByTimeSlot(UUID timeSlotId);
}
