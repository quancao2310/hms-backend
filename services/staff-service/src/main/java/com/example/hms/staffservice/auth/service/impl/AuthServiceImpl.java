package com.example.hms.staffservice.auth.service.impl;

import com.example.hms.staffservice.auth.dto.SignInRequestDTO;
import com.example.hms.staffservice.auth.dto.SignInResponseDTO;
import com.example.hms.staffservice.auth.security.impl.CustomUserDetails;
import com.example.hms.staffservice.auth.service.AuthService;
import com.example.hms.staffservice.auth.service.JwtService;
import com.example.hms.staffservice.staff.repository.StaffRepository;
import com.example.hms.staffservice.staff.service.StaffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public SignInResponseDTO signIn(SignInRequestDTO dto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails user) {
            String accessToken = jwtService.generateToken(user);
            staffRepository.updateLastLoginAt(user.getStaff().getId());
            return new SignInResponseDTO(accessToken, staffMapper.toStaffDTO(user.getStaff()));
        }
        return new SignInResponseDTO(null, null);
    }
}
