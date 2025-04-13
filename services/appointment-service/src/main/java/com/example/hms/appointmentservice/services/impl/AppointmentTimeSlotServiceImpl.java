package com.example.hms.appointmentservice.services.impl;

import com.example.hms.appointmentservice.dtos.CreateAppointmentRequestDTO;
import com.example.hms.appointmentservice.dtos.GetTimeSlotSelectionsResponseDTO;
import com.example.hms.appointmentservice.services.AppointmentService;
import com.example.hms.appointmentservice.services.AppointmentTimeSlotService;
import com.example.hms.appointmentservice.services.DoctorTimeSlotService;
import com.example.hms.appointmentservice.services.TimeSlotService;
import com.example.hms.enums.AppointmentStatus;
import com.example.hms.models.internal.appointment.Appointment;
import com.example.hms.models.internal.appointment.DoctorTimeSlot;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentTimeSlotServiceImpl implements AppointmentTimeSlotService {
    private final TimeSlotService timeSlotService;
    private final AppointmentService appointmentService;
    private final DoctorTimeSlotService doctorTimeSlotService;

    @Override
    @Transactional
    public Appointment rescheduleAppointment(UUID appointmentId, UUID timeSlotId) {
        TimeSlot timeSlot = timeSlotService.getTimeSlotById(timeSlotId);
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);

        appointment.setTimeSlot(timeSlot);
        appointment.setStatus(AppointmentStatus.RESCHEDULED);

        return appointmentService.saveAppointment(appointment);
    }

    @Override
    @Transactional
    public Appointment createAppointment(CreateAppointmentRequestDTO createAppointmentRequestDTO) {
        TimeSlot timeSlot = timeSlotService.getTimeSlotById(createAppointmentRequestDTO.getTimeSlotId());
        List<AppointmentStatus> appointmentStatuses = List.of(
                AppointmentStatus.ACCEPTED,
                AppointmentStatus.PENDING);
        Integer countAppointmentByTimeSlot = appointmentService
                .countAppointmentByTimeSlotAndStatusIn(
                        timeSlot,
                        appointmentStatuses);



        if (countAppointmentByTimeSlot.equals(timeSlot.getTotalMaxAppointment())) {
            throw new BadRequestException("Max Appointment");
        }

        return appointmentService.saveAppointment(
                Appointment.builder()
                        .date(createAppointmentRequestDTO.getDate())
                        .type(createAppointmentRequestDTO.getType())
                        .status(AppointmentStatus.PENDING)
                        .reason(createAppointmentRequestDTO.getReason())
                        .note(createAppointmentRequestDTO.getNotes())
                        .timeSlot(timeSlotService.getTimeSlotById(createAppointmentRequestDTO.getTimeSlotId()))
                        .patientAccountId(createAppointmentRequestDTO.getPatientAccountId())
                        .build()
        );
    }

    @Override
    public GetTimeSlotSelectionsResponseDTO getTimeSlotsByWeekAndDate(Integer week, DayOfWeek date) {
        List<TimeSlot> timeSlots = timeSlotService.getTimeSlotsByWeekAndDate(week, date);
        List<TimeSlot> availableTimeSlots = new ArrayList<>();
        List<TimeSlot> unavailableTimeSlots = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots) {
            if (isAvailableTimeSlot(timeSlot)) {
                availableTimeSlots.add(timeSlot);
            } else {
                unavailableTimeSlots.add(timeSlot);
            }
        }

        return new GetTimeSlotSelectionsResponseDTO(week, date, availableTimeSlots, unavailableTimeSlots);
    }

    public Boolean isAvailableTimeSlot(TimeSlot timeSlot){
//        TimeSlot timeSlot = this.getTimeSlotById(id);

        List<AppointmentStatus> appointmentStatuses = List.of(
                AppointmentStatus.ACCEPTED
        );
        Integer currentAppointmentCount = appointmentService.countAppointmentByTimeSlotAndStatusIn(
            timeSlot, appointmentStatuses
        );

        return currentAppointmentCount + 1 <= timeSlot.getTotalMaxAppointment();
    }

    @Override
    @Transactional
    public TimeSlot removeTimeSlot(UUID timeSlotID) {
        TimeSlot deletedTimeSlot = timeSlotService.getTimeSlotById(timeSlotID);
        List<Appointment> appointments = appointmentService.getAppointmentByTimeSlot(deletedTimeSlot);
        List<DoctorTimeSlot> deletedDoctorTimeSlots = doctorTimeSlotService.getDoctorTimeSlotsByTimeSlot(deletedTimeSlot);

        doctorTimeSlotService.removeDoctorTimeSlots(deletedDoctorTimeSlots);

        for (Appointment appointment : appointments) {
            appointment.setTimeSlot(null);
            appointment.setStatus(AppointmentStatus.CANCELLED);

            appointmentService.saveAppointment(appointment);
        }

        return timeSlotService.removeTimeSlot(timeSlotID);
    }

    @Override
    @Transactional
    public List<TimeSlot> removeTimeSlots(List<UUID> timeSlotIds) {
        List<TimeSlot> deletedTimeSlots = timeSlotService.getTimeSlotsByIds(timeSlotIds);
        List<Appointment> appointments = appointmentService.getAppointmentByTimeSlots(deletedTimeSlots);
        List<DoctorTimeSlot> deletedDoctorTimeSlots = new ArrayList<>();

        for (TimeSlot timeSlot : deletedTimeSlots) {
            deletedDoctorTimeSlots.addAll(
                    doctorTimeSlotService.getDoctorTimeSlotsByTimeSlot(timeSlot)
            );
        }

        doctorTimeSlotService.removeDoctorTimeSlots(deletedDoctorTimeSlots);

        for (Appointment appointment : appointments) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            appointment.setTimeSlot(null);

            appointmentService.saveAppointment(appointment);
        }

        return timeSlotService.removeTimeSlots(timeSlotIds);
    }

    @Override
    @Transactional
    public List<DoctorTimeSlot> removeDoctorTimeslots(List<UUID> timeSlotIds, UUID doctorId) {
        List<TimeSlot> timeSlots = timeSlotService.getTimeSlotsByIds(timeSlotIds);
        List<DoctorTimeSlot> result = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlots) {
            List<Appointment> appointments = appointmentService.getAppointmentByTimeSlot(timeSlot);
            for (Appointment appointment : appointments) {
                if (appointment.getDoctor().getId().equals(doctorId)) {
                    appointment.setStatus(AppointmentStatus.CANCELLED);

                    appointmentService.saveAppointment(appointment);
                }
            }
            Optional<DoctorTimeSlot> doctorTimeSlot = doctorTimeSlotService.getDoctorTimeSlotById(
                    doctorId, timeSlot.getId()
            );
            doctorTimeSlot.ifPresent(result::add);
        }

        doctorTimeSlotService.removeDoctorTimeSlots(result);

        return result;
    }
}
