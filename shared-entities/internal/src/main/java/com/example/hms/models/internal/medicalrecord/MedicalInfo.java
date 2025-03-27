package com.example.hms.models.internal.medicalrecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer height; // in cm
    private BigDecimal weight; // in kg
    private BigDecimal bmi;
    private String bloodType;
    private String bloodPressure;

    @OneToOne(mappedBy = "medicalInfo")
    private Patient patient;

    @PrePersist
    @PreUpdate
    public void calculateBmi() {
        if (height != null && weight != null) {
            BigDecimal heightInMeters = BigDecimal.valueOf(height)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            bmi = weight.divide(heightInMeters.pow(2), 2, RoundingMode.HALF_UP);
        }
    }
}
