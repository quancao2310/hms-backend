package com.example.hms.models.internal.staff;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@PrimaryKeyJoinColumn(name = "doctor_id")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Doctor extends Staff {

    private String specializations;
    private String licenseNumber;
    private String qualification;
    private String department;
}
