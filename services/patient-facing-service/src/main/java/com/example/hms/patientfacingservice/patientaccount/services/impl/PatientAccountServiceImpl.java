package com.example.hms.patientfacingservice.patientaccount.services.impl;

import com.example.hms.models.external.patientaccount.PatientAccount;
import com.example.hms.patientfacingservice.auth.exceptions.DuplicateEmailException;
import com.example.hms.patientfacingservice.auth.security.impl.CustomUserDetails;
import com.example.hms.patientfacingservice.common.dtos.MessageDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.PatientAccountDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.UpdateAccountDTO;
import com.example.hms.patientfacingservice.patientaccount.dtos.UpdatePasswordDTO;
import com.example.hms.patientfacingservice.patientaccount.exceptions.InvalidPasswordException;
import com.example.hms.patientfacingservice.patientaccount.repositories.PatientAccountRepository;
import com.example.hms.patientfacingservice.patientaccount.services.PatientAccountMapper;
import com.example.hms.patientfacingservice.patientaccount.services.PatientAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientAccountServiceImpl implements PatientAccountService {

    private final PatientAccountRepository patientAccountRepository;
    private final PatientAccountMapper patientAccountMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PatientAccountDTO getProfile(CustomUserDetails user) {
        return patientAccountMapper.toPatientAccountDTO(user.getPatientAccount());
    }

    @Override
    public Object updateUserDetails(CustomUserDetails user, UpdateAccountDTO dto) {
        String oldEmail = user.getEmail();
        if (!dto.email().equals(oldEmail) && patientAccountRepository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException();
        }

        PatientAccount patientAccount = user.getPatientAccount();
        patientAccount.setEmail(dto.email());
        patientAccount.setFullName(dto.fullName());
        patientAccount.setSsn(dto.ssn());
        patientAccount.setPhoneNumber(dto.phoneNumber());

        PatientAccount updatedPatientAccount = patientAccountRepository.save(patientAccount);
        if (!oldEmail.equals(updatedPatientAccount.getEmail())) {
            return new MessageDTO("Email updated successfully. Please sign in again.");
        }
        return patientAccountMapper.toPatientAccountDTO(updatedPatientAccount);
    }

    @Override
    public MessageDTO changePassword(CustomUserDetails user, UpdatePasswordDTO dto) {
        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        patientAccountRepository.updatePassword(user.getPatientAccount().getId(), passwordEncoder.encode(dto.newPassword()));
        return new MessageDTO("Password updated successfully. Please sign in again.");
    }
}
