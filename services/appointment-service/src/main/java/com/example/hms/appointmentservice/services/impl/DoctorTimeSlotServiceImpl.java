package com.example.hms.appointmentservice.services.impl;

import com.example.hms.appointmentservice.dtos.*;
import com.example.hms.appointmentservice.repositories.DoctorTimeSlotRepository;
import com.example.hms.appointmentservice.services.*;
import com.example.hms.enums.AppointmentStatus;
import com.example.hms.models.internal.appointment.Appointment;
import com.example.hms.models.internal.appointment.DoctorTimeSlot;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Admin;
import com.example.hms.models.internal.staff.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public List<DoctorTimeSlot> createDoctorTimeSlot(CreateDoctorTimeSlotRequestDTO createDoctorTimeSlotRequestDTO) {
        Admin admin =  adminService.getAdminById(createDoctorTimeSlotRequestDTO.getAssignedBy());
        List<TimeSlot> timeSlots = timeSlotService.getTimeSlotsByIds(createDoctorTimeSlotRequestDTO.getTimeSlotIds());
        List<Doctor> doctors = doctorService.getDoctorsByIds(createDoctorTimeSlotRequestDTO.getDoctorIds());
        List<DoctorTimeSlot> result = new ArrayList<>();

        for (Doctor doctor : doctors) {
            for (TimeSlot timeSlot : timeSlots) {
                Optional<DoctorTimeSlot> doctorTimeSlot = doctorTimeSlotRepository.findByDoctor_IdAndTimeSlot_Id(
                        doctor.getId(), timeSlot.getId()
                );

                if (doctorTimeSlot.isPresent()) {
                    continue;
                } else {
                    result.add(doctorTimeSlotRepository.save(
                            DoctorTimeSlot.builder()
                                    .maxAppointment(createDoctorTimeSlotRequestDTO.getMaxAppointments())
                                    .assignedBy(admin)
                                    .doctor(doctor)
                                    .timeSlot(timeSlot)
                                    .build()
                    ));
                }
            }
        }

        return doctorTimeSlotRepository.saveAll(result);
    }

    @Override
    @Transactional
    public List<DoctorTimeSlot> registerDoctorTimeSlots(CreateBulkDoctorTimeSlotRequestDTO createBulkDoctorTimeSlotRequestDTO) {
        List<DoctorTimeSlot> result = new ArrayList<>();
        Doctor doctor = doctorService.getDoctorById(createBulkDoctorTimeSlotRequestDTO.getDoctorId());
        List<TimeSlot> timeSlots = timeSlotService.getTimeSlotsByIds(createBulkDoctorTimeSlotRequestDTO.getTimeSlotIds());
        Integer maxAppointmentsPerTimeSlot = createBulkDoctorTimeSlotRequestDTO.getMaxAppointmentsPerTimeSlot();

        for (TimeSlot timeSlot : timeSlots) {
            Optional<DoctorTimeSlot> doctorTimeSlot = doctorTimeSlotRepository.findByDoctor_IdAndTimeSlot_Id(
                    doctor.getId(), timeSlot.getId()
            );

            if (doctorTimeSlot.isPresent()) {
                continue;
            } else {
                result.add(DoctorTimeSlot.builder()
                                .timeSlot(timeSlot)
                                .doctor(doctor)
                                .maxAppointment(maxAppointmentsPerTimeSlot)
                                .build()
                );
            }
        }

        return doctorTimeSlotRepository.saveAll(result);
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

    @Override
    public Optional<DoctorTimeSlot> getDoctorTimeSlotById(UUID doctorId, UUID timeSlotId) {
        return doctorTimeSlotRepository.findByDoctor_IdAndTimeSlot_Id(doctorId, timeSlotId);
    }

    @Override
    public void removeDoctorTimeSlots(List<DoctorTimeSlot> doctorTimeSlots) {
        doctorTimeSlotRepository.deleteAll(doctorTimeSlots);
    }

    @Override
    public List<DoctorTimeSlot> getDoctorTimeSlotsByTimeSlot(TimeSlot timeSlot) {
        return doctorTimeSlotRepository.findByTimeSlot(timeSlot);
    }

    @Override
    public List<DoctorTimeSlotDTO> getTimeSlotsForDoctor(UUID doctorId) {
        Doctor doctor = doctorService.getDoctorById(doctorId);
        List<DoctorTimeSlot> doctorTimeSlots = doctorTimeSlotRepository.findByDoctor(doctor);
        List<DoctorTimeSlotDTO> doctorTimeSlotDTOS = new ArrayList<>();

        for (DoctorTimeSlot doctorTimeSlot : doctorTimeSlots) {
            List<AppointmentInfoDTO> appointmentInfoDTOSs = appointmentService
                    .getAppointmentInfoByTimeSlotAndDoctor(
                            doctorTimeSlot.getTimeSlot(), doctor);

            doctorTimeSlotDTOS.add(
                    new DoctorTimeSlotDTO(doctorTimeSlot.getId(),
                            doctorTimeSlot.getMaxAppointment(),
                            doctorTimeSlot.getTimeSlot(),
                            doctorTimeSlot.getDoctor(),
                            doctorTimeSlot.getAssignedBy(),
                            appointmentInfoDTOSs)
            );
        }

        return doctorTimeSlotDTOS;
    }
}
