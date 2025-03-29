package com.example.hms.models.internal.medicalrecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String dosage;
    private String frequency;
    private LocalDate startDate;
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "medical_history_id")
    private MedicalHistory medicalHistory;
}
