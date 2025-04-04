package com.example.hms.patientfacingservice.patientaccount.services;

import com.example.hms.models.external.patientaccount.PatientAccount;
import com.example.hms.patientfacingservice.auth.dtos.SignUpRequestDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.PatientAccountDTO;
import org.springframework.stereotype.Service;

@Service
public interface PatientAccountMapper {
    PatientAccount toPatientAccount(SignUpRequestDTO dto);

    PatientAccountDTO toPatientAccountDTO(PatientAccount patientAccount);
}
