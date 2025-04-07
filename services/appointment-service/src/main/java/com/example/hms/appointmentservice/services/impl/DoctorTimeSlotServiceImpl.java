package com.example.hms.appointmentservice.services.impl;

import com.example.hms.appointmentservice.dtos.CreateDoctorTimeSlotRequestDTO;
import com.example.hms.appointmentservice.dtos.CreateBulkDoctorTimeSlotRequestDTO;
import com.example.hms.appointmentservice.dtos.ModifyMaxAppointmentForDoctorRequestDTO;
import com.example.hms.appointmentservice.dtos.DistributeTimeSlotForDoctorRequestDTO;
import com.example.hms.appointmentservice.repositories.DoctorTimeSlotRepository;
import com.example.hms.appointmentservice.services.*;
import com.example.hms.enums.AppointmentStatus;
import com.example.hms.models.internal.appointment.DoctorTimeSlot;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Admin;
import com.example.hms.models.internal.staff.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorTimeSlotServiceImpl implements DoctorTimeSlotService {
    private final DoctorTimeSlotRepository doctorTimeSlotRepository;
    private final DoctorService doctorService;
    private final TimeSlotService timeSlotService;
    private final AdminService adminService;
    private final AppointmentService appointmentService;

    @Override
    @Transactional
    public DoctorTimeSlot createDoctorTimeSlot(CreateDoctorTimeSlotRequestDTO createDoctorTimeSlotRequestDTO) {
        Admin admin =  adminService.getAdminById(createDoctorTimeSlotRequestDTO.getAssignedBy());

        return doctorTimeSlotRepository.save(
                DoctorTimeSlot.builder()
                        .maxAppointment(createDoctorTimeSlotRequestDTO.getMaxAppointments())
                        .assignedBy(admin)
                        .doctor(createDoctorTimeSlotRequestDTO.getDoctor())
                        .timeSlot(createDoctorTimeSlotRequestDTO.getTimeSlot())
                        .build()
        );
    }

    @Override
    @Transactional
    public List<DoctorTimeSlot> registerDoctorTimeSlots(CreateBulkDoctorTimeSlotRequestDTO createBulkDoctorTimeSlotRequestDTO) {
        List<DoctorTimeSlot> result = new ArrayList<>();
        Doctor doctor = doctorService.getDoctorById(createBulkDoctorTimeSlotRequestDTO.getDoctorId());
        List<TimeSlot> timeSlots = timeSlotService.getTimeSlotsByIds(createBulkDoctorTimeSlotRequestDTO.getTimeSlotIds());
        Integer maxAppointmentsPerTimeSlot = createBulkDoctorTimeSlotRequestDTO.getMaxAppointmentsPerTimeSlot();

        for (TimeSlot timeSlot : timeSlots) {
            DoctorTimeSlot doctorTimeSlot = doctorTimeSlotRepository.save(
                    DoctorTimeSlot.builder()
                            .timeSlot(timeSlot)
                            .doctor(doctor)
                            .maxAppointment(maxAppointmentsPerTimeSlot)
                            .build()
            );

            result.add(doctorTimeSlot);
        }

        return result;
    }

    @Override
    @Transactional
    public List<DoctorTimeSlot> distributeTimeSlotsForDoctors(DistributeTimeSlotForDoctorRequestDTO distributeTimeSlotForDoctorRequestDTO) {
        List<DoctorTimeSlot> result = new ArrayList<>();

        List<Doctor> doctors = doctorService.getAllDoctors();
        List<TimeSlot> timeSlots = timeSlotService.getTimeSlotsByIds(distributeTimeSlotForDoctorRequestDTO.getTimeSlotIds());
        Integer maxAppointmentsPerTimeSlot = distributeTimeSlotForDoctorRequestDTO.getMaxAppointmentsPerTimeSlot();

        for (Doctor doctor : doctors) {
            for (TimeSlot timeSlot : timeSlots) {
                result.add(
                        doctorTimeSlotRepository.save(
                                DoctorTimeSlot.builder()
                                        .doctor(doctor)
                                        .timeSlot(timeSlot)
                                        .maxAppointment(maxAppointmentsPerTimeSlot)
                                        .build()
                        )
                );
            }
        }

        return result;
    }

    @Override
    @Transactional
    public List<DoctorTimeSlot> setMaxAppointmentsPerTimeSlotsForDoctor(ModifyMaxAppointmentForDoctorRequestDTO modifyMaxAppointmentForDoctorRequestDTO) {
        List<DoctorTimeSlot> result = new ArrayList<>();

        Doctor doctor = doctorService.getDoctorById(modifyMaxAppointmentForDoctorRequestDTO.getDoctorId());
        List<DoctorTimeSlot> doctorTimeSlots = doctorTimeSlotRepository.findAllById(modifyMaxAppointmentForDoctorRequestDTO.getDoctorTimeSlotIds());
        Integer maxAppointment = modifyMaxAppointmentForDoctorRequestDTO.getMaxAppointment();

        for (DoctorTimeSlot doctorTimeSlot : doctorTimeSlots) {
            doctorTimeSlot.setMaxAppointment(maxAppointment);

            result.add(
                    doctorTimeSlotRepository.save(doctorTimeSlot)
            );
        }

        return result;
    }

    @Override
    public List<Doctor> getAvailableDoctorByTimeSlot(UUID timeSlotId) {
        TimeSlot timeSlot = timeSlotService.getTimeSlotById(timeSlotId);
        List<Doctor> result = new ArrayList<>();
        List<DoctorTimeSlot> doctorTimeSlots = doctorTimeSlotRepository.findByTimeSlot(timeSlot);

        for (DoctorTimeSlot doctorTimeSlot : doctorTimeSlots) {
            if (isAvailableDoctor(doctorTimeSlot)) {
                result.add(doctorTimeSlot.getDoctor());
            }
        }

        return  result;
    }

    public Boolean isAvailableDoctor(DoctorTimeSlot doctorTimeSlot) {
        Integer countAppointmentByDoctorAndTimeSlot = appointmentService
                .countAppointmentByDoctorAndTimeSlotAndStatus(
                        doctorTimeSlot.getDoctor(),
                        doctorTimeSlot.getTimeSlot(),
                        AppointmentStatus.ACCEPTED);

        return countAppointmentByDoctorAndTimeSlot + 1 <= doctorTimeSlot.getMaxAppointment();

    }
}
