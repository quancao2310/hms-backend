package com.example.hms.patientfacingservice.patientaccount.services;

import com.example.hms.patientfacingservice.auth.security.impl.CustomUserDetails;
import com.example.hms.patientfacingservice.common.dtos.MessageDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.PatientAccountDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.UpdateAccountDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.UpdatePasswordDTO;
import org.springframework.stereotype.Service;

@Service
public interface PatientAccountService {
    PatientAccountDTO getProfile(CustomUserDetails user);

    Object updateUserDetails(CustomUserDetails user, UpdateAccountDTO dto);

    MessageDTO changePassword(CustomUserDetails user, UpdatePasswordDTO dto);
}
