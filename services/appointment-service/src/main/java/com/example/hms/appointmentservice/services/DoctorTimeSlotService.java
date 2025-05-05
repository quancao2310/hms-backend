package com.example.hms.appointmentservice.services;

import com.example.hms.appointmentservice.dtos.*;
import com.example.hms.models.internal.appointment.DoctorTimeSlot;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorTimeSlotService {
    List<DoctorTimeSlot> createDoctorTimeSlot(CreateDoctorTimeSlotRequestDTO createDoctorTimeSlotRequestDTO);
    List<DoctorTimeSlot> registerDoctorTimeSlots(CreateBulkDoctorTimeSlotRequestDTO createBulkDoctorTimeSlotRequestDTO);
    List<DoctorTimeSlot> distributeTimeSlotsForDoctors(DistributeTimeSlotForDoctorRequestDTO distributeTimeSlotForDoctorRequestDTO);
    List<DoctorTimeSlot> setMaxAppointmentsPerTimeSlotsForDoctor(ModifyMaxAppointmentForDoctorRequestDTO modifyMaxAppointmentForDoctorRequestDTO);
    List<Doctor> getAvailableDoctorByTimeSlot(UUID timeSlotId);
    Optional<DoctorTimeSlot> getDoctorTimeSlotById(UUID doctorId, UUID timeSlotId);
    void removeDoctorTimeSlots(List<DoctorTimeSlot> doctorTimeSlots);
    List<DoctorTimeSlot> getDoctorTimeSlotsByTimeSlot(TimeSlot timeSlot);
    List<DoctorTimeSlotDTO> getTimeSlotsForDoctor(UUID doctorId);
}
