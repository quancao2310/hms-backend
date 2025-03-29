package com.example.hms.models.internal.medicalrecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
