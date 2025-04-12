package com.example.hms.appointmentservice.repositories;

import com.example.hms.models.internal.appointment.DoctorTimeSlot;
import com.example.hms.models.internal.appointment.TimeSlot;
import com.example.hms.models.internal.staff.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DoctorTimeSlotRepository extends JpaRepository<DoctorTimeSlot, UUID> {
    List<DoctorTimeSlot> findByTimeSlot(TimeSlot timeSlot);
    Optional<DoctorTimeSlot> findByDoctor_IdAndTimeSlot_Id(UUID doctorId, UUID timeSlotId);
}
