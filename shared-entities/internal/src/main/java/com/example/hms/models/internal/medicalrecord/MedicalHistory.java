package com.example.hms.models.internal.medicalrecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "medicalHistory")
    private Patient patient;

    @OneToMany(mappedBy = "medicalHistory")
    private List<SurgicalHistory> surgicalHistories;

    @OneToMany(mappedBy = "medicalHistory")
    private List<PastDisease> pastDiseases;

    @OneToMany(mappedBy = "medicalHistory")
    private List<FamilyHistory> familyHistories;

    @OneToMany(mappedBy = "medicalHistory")
    private List<MedicationHistory> medicationHistories;

    @OneToMany(mappedBy = "medicalHistory")
    private List<Vaccination> vaccinations;

    @OneToMany(mappedBy = "medicalHistory")
    private List<Allergy> allergies;
}
