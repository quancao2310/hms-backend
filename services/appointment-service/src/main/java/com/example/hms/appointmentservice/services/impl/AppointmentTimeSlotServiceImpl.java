package com.example.hms.appointmentservice.services.impl;

import com.example.hms.appointmentservice.dtos.CreateAppointmentRequestDTO;
import com.example.hms.appointmentservice.dtos.GetTimeSlotSelectionsResponseDTO;
import com.example.hms.appointmentservice.services.AppointmentService;
import com.example.hms.appointmentservice.services.AppointmentTimeSlotService;
import com.example.hms.appointmentservice.services.TimeSlotService;
import com.example.hms.enums.AppointmentStatus;
import com.example.hms.models.internal.appointment.Appointment;
import com.example.hms.models.internal.appointment.TimeSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentTimeSlotServiceImpl implements AppointmentTimeSlotService {
    private final TimeSlotService timeSlotService;
    private final AppointmentService appointmentService;

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
}
