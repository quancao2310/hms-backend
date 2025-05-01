package com.example.hms.appointmentservice.services.impl;

import com.example.hms.appointmentservice.dtos.AppointmentInfoDTO;
import com.example.hms.appointmentservice.dtos.CreateAppointmentRequestDTO;
import com.example.hms.appointmentservice.dtos.SearchRequestDTO;
import com.example.hms.appointmentservice.repositories.AppointmentRepository;
import com.example.hms.appointmentservice.services.*;
import com.example.hms.enums.AppointmentStatus;
import com.example.hms.models.internal.appointment.Appointment;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final BaseSearchService baseSearchService;

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

    @Override
    public Integer countAppointmentByDoctorAndTimeSlotAndStatus(
            Doctor doctor, TimeSlot timeSlot, AppointmentStatus appointmentStatus) {
        return appointmentRepository.countByDoctorAndTimeSlotAndStatus(doctor, timeSlot, appointmentStatus);
    }

    @Override
    public Integer countAppointmentByTimeSlotAndStatusIn(
            TimeSlot timeSlot, List<AppointmentStatus> appointmentStatuses
    ) {
        return appointmentRepository.countByTimeSlotAndStatusIn(
                timeSlot, appointmentStatuses
        );
    }

    @Override
    public Page<Appointment> searchAppointments(SearchRequestDTO requestDTO) {
        return baseSearchService.search(Appointment.class, appointmentRepository, requestDTO);
    }

    @Scheduled(cron = "0 0 1 * * *", zone = "Asia/Ho_Chi_Minh")
    public void updateAppointmentStatus() {
        List<AppointmentStatus> statuses = List.of(
                AppointmentStatus.ACCEPTED,
                AppointmentStatus.PENDING,
                AppointmentStatus.RESCHEDULED
        );
        List<Appointment> appointments = appointmentRepository.findByStatusInAndDateBefore(
          statuses, LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"))
        );
        List<Appointment> canceledAppointment = new ArrayList<>();
        for (Appointment appointment : appointments) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            canceledAppointment.add(appointment);
        }
        appointmentRepository.saveAll(canceledAppointment);
    }

    @Override
    public List<Appointment> getAppointmentByTimeSlot(TimeSlot timeSlot) {
        return appointmentRepository.findByTimeSlot(timeSlot);
    }

    @Override
    public List<Appointment> getAppointmentByTimeSlots(List<TimeSlot> timeSlots) {
        return appointmentRepository.findByTimeSlotIn(timeSlots);
    }

    @Override
    public List<AppointmentInfoDTO> getAppointmentInfoByTimeSlotAndDoctor(TimeSlot timeSlot, Doctor doctor) {
        return appointmentRepository.findAppointmentInfoByTimeSlotAndDoctor(timeSlot, doctor);
    }
}
