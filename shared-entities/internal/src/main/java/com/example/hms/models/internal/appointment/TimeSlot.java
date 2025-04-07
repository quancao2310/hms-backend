package com.example.hms.models.internal.appointment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer week;

    @Enumerated(EnumType.STRING)
    private DayOfWeek date;

    private LocalTime startTime;
    private LocalTime endTime;

    private Integer totalMaxAppointment;
}
