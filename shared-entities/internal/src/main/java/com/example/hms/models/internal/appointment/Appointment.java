package com.example.hms.models.internal.appointment;

import com.example.hms.enums.AppointmentStatus;
import com.example.hms.enums.AppointmentType;
import com.example.hms.models.internal.staff.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.List;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate date;

    @Enumerated(value = EnumType.STRING)
    private AppointmentType type;
    @Enumerated(value = EnumType.STRING)
    private AppointmentStatus status;

    private String reason;

    @ElementCollection
    @CollectionTable(name = "note_files", joinColumns = @JoinColumn(name = "appointment_id"))
    @Column(name = "notes")
    private List<String> note;

    @ManyToOne
    @JoinColumn(name = "timeslot_id")
    private TimeSlot timeSlot;

    private UUID patientAccountId;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
}
