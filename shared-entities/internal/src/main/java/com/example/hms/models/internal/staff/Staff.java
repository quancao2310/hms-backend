package com.example.hms.models.internal.staff;

import com.example.hms.enums.Sex;
import com.example.hms.enums.UserRole;
import com.example.hms.enums.WorkingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String ssn;

    private LocalDate dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    private Sex sex;
    private String phoneNumber;
    private String nationality;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    private LocalDate startWorkingDate;

    @Enumerated(value = EnumType.STRING)
    private WorkingStatus status;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    private Instant lastLoginAt;
}
