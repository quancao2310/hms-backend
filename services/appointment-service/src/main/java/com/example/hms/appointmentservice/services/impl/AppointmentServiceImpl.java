package com.example.hms.appointmentservice.services.impl;

import com.example.hms.appointmentservice.dtos.CreateAppointmentRequestDTO;
import com.example.hms.appointmentservice.repositories.AppointmentRepository;
import com.example.hms.appointmentservice.services.AppointmentService;
import com.example.hms.appointmentservice.services.DoctorService;
import com.example.hms.appointmentservice.services.DoctorTimeSlotService;
import com.example.hms.appointmentservice.services.TimeSlotService;
import com.example.hms.enums.AppointmentStatus;
import com.example.hms.models.internal.appointment.Appointment;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;

    @Override
    @Transactional
    public Appointment saveAppointment(Appointment appointment){
        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public Appointment assignDoctorForAppointment(UUID appointmentId, UUID doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        Appointment appointment = this.getAppointmentById(appointmentId);

        appointment.setDoctor(doctor);

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentById(UUID id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));
    }

    @Override
    @Transactional
    public Appointment acceptAppointment(UUID id) {
        Appointment appointment = this.getAppointmentById(id);

        appointment.setStatus(AppointmentStatus.ACCEPTED);

        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public Appointment declineAppointment(UUID id) {
        Appointment appointment = this.getAppointmentById(id);

        appointment.setStatus(AppointmentStatus.REJECTED);

        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public Appointment completeAppointment(UUID id) {
        Appointment appointment = this.getAppointmentById(id);

        appointment.setStatus(AppointmentStatus.COMPLETED);

        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public Appointment cancelAppointment(UUID id) {
        Appointment appointment = this.getAppointmentById(id);

        appointment.setStatus(AppointmentStatus.CANCELLED);

        return appointmentRepository.save(appointment);
    }

    @Override
    public Integer countAppointmentByTimeSlot(TimeSlot timeSlot) {
        return appointmentRepository.countByTimeSlot(timeSlot);
    }

    @Override
    public Integer countAppointmentByDoctorAndTimeSlot(Doctor doctor, TimeSlot timeSlot) {
        return appointmentRepository.countByDoctorAndTimeSlot(doctor, timeSlot);
    }
}
