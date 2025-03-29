package com.example.hms.models.internal.medicalrecord;

import com.example.hms.enums.MaritalStatus;
import com.example.hms.enums.Sex;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fullName;

    @Column(unique = true)
    private String ssn;

    private LocalDate dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    private Sex sex;
    private String nationality;
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String address;

    private String occupation;

    @Enumerated(value = EnumType.STRING)
    private MaritalStatus maritalStatus;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<PatientRelative> relatives;

    @OneToOne
    @JoinColumn(name = "medical_info_id")
    private MedicalInfo medicalInfo;

    @OneToOne
    @JoinColumn(name = "medical_history_id")
    private MedicalHistory medicalHistory;
}
