package com.example.hms.patientfacingservice.patientaccount.services.impl;

import com.example.hms.models.external.patientaccount.PatientAccount;
import com.example.hms.patientfacingservice.auth.dtos.SignUpRequestDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.PatientAccountDTO;
import com.example.hms.patientfacingservice.patientaccount.services.PatientAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientAccountMapperImpl implements PatientAccountMapper {

    private final PasswordEncoder passwordEncoder;

    @Override
    public PatientAccount toPatientAccount(SignUpRequestDTO dto) {
        return PatientAccount.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .fullName(dto.fullName())
                .ssn(dto.ssn())
                .phoneNumber(dto.phoneNumber())
                .build();
    }

    @Override
    public PatientAccountDTO toPatientAccountDTO(PatientAccount patientAccount) {
        if (patientAccount == null) {
            return null;
        }

        return new PatientAccountDTO(
                patientAccount.getId(),
                patientAccount.getFullName(),
                patientAccount.getEmail(),
                patientAccount.getSsn(),
                patientAccount.getPhoneNumber(),
                patientAccount.getCreatedAt(),
                patientAccount.getLastLoginAt()
        );
    }
}
