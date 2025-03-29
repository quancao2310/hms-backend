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
public class PatientRelative {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fullName;
    private String relationship;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
