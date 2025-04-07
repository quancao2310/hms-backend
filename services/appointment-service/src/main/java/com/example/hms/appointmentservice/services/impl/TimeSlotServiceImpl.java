package com.example.hms.appointmentservice.services.impl;

import com.example.hms.appointmentservice.dtos.CreateBulkTimeSlotRequestDTO;
import com.example.hms.appointmentservice.dtos.CreateTimeSlotRequestDTO;
import com.example.hms.appointmentservice.dtos.GetTimeSlotSelectionsResponseDTO;
import com.example.hms.appointmentservice.repositories.TimeSlotRepository;
import com.example.hms.appointmentservice.services.AppointmentService;
import com.example.hms.appointmentservice.services.DoctorTimeSlotService;
import com.example.hms.appointmentservice.services.TimeSlotService;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;

    @Override
    @Transactional
    public List<TimeSlot> createBulkTimeSlots(CreateBulkTimeSlotRequestDTO createBulkTimeSlotRequestDTO) {
        List<TimeSlot> result = new ArrayList<>();
        Integer durationMinutes = createBulkTimeSlotRequestDTO.getDurationMinutes();
        Integer maxAppointmentPerTimeSlot = createBulkTimeSlotRequestDTO.getMaxAppointmentPerTimeSLot();
        Integer week = createBulkTimeSlotRequestDTO.getWeek();

        for (DayOfWeek dayOfWeek: createBulkTimeSlotRequestDTO.getDates()) {
            LocalTime slotStart = createBulkTimeSlotRequestDTO.getStartTime();
            LocalTime slotEnd = createBulkTimeSlotRequestDTO.getEndTime();

            while (slotStart.plusMinutes(durationMinutes).isBefore(slotEnd) ||
                    slotEnd.plusMinutes(durationMinutes).equals(slotEnd)) {

                result.add(
                        TimeSlot.builder()
                                .week(week)
                                .date(dayOfWeek)
                                .startTime(slotStart)
                                .endTime(slotStart.plusMinutes(durationMinutes))
                                .totalMaxAppointment(maxAppointmentPerTimeSlot)
                                .build()
                );

                slotStart = slotStart.plusMinutes(durationMinutes);
            }
        }

        return timeSlotRepository.saveAll(result);
    }

    public List<TimeSlot> getTimeSlotsByIds(List<UUID> ids) {
        return timeSlotRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public TimeSlot createTimeSlot(CreateTimeSlotRequestDTO createTimeSlotRequestDTO) {

        return timeSlotRepository.save(
                TimeSlot.builder()
                        .week(createTimeSlotRequestDTO.getWeek())
                        .date(createTimeSlotRequestDTO.getDate())
                        .startTime(createTimeSlotRequestDTO.getStartTime())
                        .endTime(createTimeSlotRequestDTO.getEndTime())
                        .totalMaxAppointment(createTimeSlotRequestDTO.getMaxAppointmentPerTimeSLot())
                        .build()
        );
    }

    @Override
    @Transactional
    public TimeSlot removeTimeSlot(UUID timeSlotID) {
        TimeSlot deletedTimeSlot = this.getTimeSlotById(timeSlotID);

        timeSlotRepository.deleteById(timeSlotID);

        return deletedTimeSlot;
    }

    @Override
    public TimeSlot getTimeSlotById(UUID timeSlotID) {
        return timeSlotRepository.findById(timeSlotID)
                .orElseThrow(() -> new NotFoundException("Time Slot not found"));
    }

    @Override
    public List<TimeSlot> getTimeSlotsByWeekAndDate(Integer week, DayOfWeek date) {
        return timeSlotRepository.findByWeekAndDate(week, date);
    }
}
