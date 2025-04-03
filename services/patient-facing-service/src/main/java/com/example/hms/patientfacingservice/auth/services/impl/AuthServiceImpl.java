package com.example.hms.patientfacingservice.auth.services.impl;

import com.example.hms.models.external.patientaccount.PatientAccount;
import com.example.hms.patientfacingservice.auth.dtos.SignInRequestDTO;
import com.example.hms.patientfacingservice.auth.dtos.SignInResponseDTO;
import com.example.hms.patientfacingservice.auth.dtos.SignUpRequestDTO;
import com.example.hms.patientfacingservice.auth.exceptions.DuplicateEmailException;
import com.example.hms.patientfacingservice.auth.exceptions.DuplicateSsnException;
import com.example.hms.patientfacingservice.auth.services.JwtService;
import com.example.hms.patientfacingservice.patientaccount.services.PatientAccountMapper;
import com.example.hms.patientfacingservice.auth.services.AuthService;
import com.example.hms.patientfacingservice.patientaccount.dtos.PatientAccountDTO;
import com.example.hms.patientfacingservice.patientaccount.repositories.PatientAccountRepository;
import com.example.hms.patientfacingservice.auth.security.impl.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PatientAccountRepository patientAccountRepository;
    private final PatientAccountMapper patientAccountMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public PatientAccountDTO signUp(SignUpRequestDTO dto) {
        if (patientAccountRepository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException();
        }
        if (patientAccountRepository.existsBySsn(dto.ssn())) {
            throw new DuplicateSsnException();
        }

        PatientAccount patientAccount = patientAccountMapper.toPatientAccount(dto);
        PatientAccount savedPatientAccount = patientAccountRepository.save(patientAccount);
        return patientAccountMapper.toPatientAccountDTO(savedPatientAccount);
    }

    @Override
    public SignInResponseDTO signIn(SignInRequestDTO dto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails user) {
            String accessToken = jwtService.generateToken(user);
            patientAccountRepository.updateLastLoginAt(user.getPatientAccount().getId());
            return new SignInResponseDTO(accessToken, patientAccountMapper.toPatientAccountDTO(user.getPatientAccount()));
        }
        return new SignInResponseDTO(null, null);
    }
}
