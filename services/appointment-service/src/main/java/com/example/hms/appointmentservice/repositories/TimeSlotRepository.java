package com.example.hms.appointmentservice.repositories;

import com.example.hms.models.internal.appointment.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.util.UUID;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, UUID> {
    @Query("""
            SELECT t FROM TimeSlot t
            WHERE (:week IS NULL OR t.week = :week)
            AND (:date IS NULL OR t.date = :date)
            """)
    List<TimeSlot> findByWeekAndDate(@Param("week") Integer week, @Param("date") DayOfWeek date);
}
