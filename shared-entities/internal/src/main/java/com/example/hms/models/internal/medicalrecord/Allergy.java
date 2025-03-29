package com.example.hms.models.internal.medicalrecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Allergy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String allergen;
    private String severity;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "medical_history_id")
    private MedicalHistory medicalHistory;
}
