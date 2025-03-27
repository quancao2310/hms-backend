package com.example.hms.models.external.patientaccount;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @Column(unique = true)
    private String ssn;
    private String phoneNumber;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    private Instant lastLoginAt;
}
