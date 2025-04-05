package com.example.hms.staffservice.auth.service.impl;

import com.example.hms.staffservice.auth.dto.SignInRequestDTO;
import com.example.hms.staffservice.auth.dto.SignInResponseDTO;
import com.example.hms.staffservice.auth.security.impl.CustomUserDetails;
import com.example.hms.staffservice.auth.service.AuthService;
import com.example.hms.staffservice.auth.service.JwtService;
import com.example.hms.staffservice.common.dto.MessageDTO;
import com.example.hms.staffservice.staff.dto.StaffDTO;
import com.example.hms.staffservice.staff.dto.UpdatePasswordDTO;
import com.example.hms.staffservice.staff.exception.InvalidPasswordException;
import com.example.hms.staffservice.staff.repository.StaffRepository;
import com.example.hms.staffservice.staff.service.StaffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public StaffDTO getProfile(CustomUserDetails user) {
        return staffMapper.toStaffDTO(user.getStaff());
    }

    @Override
    public MessageDTO changePassword(CustomUserDetails user, UpdatePasswordDTO dto) {
        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        staffRepository.updatePassword(user.getStaff().getId(), passwordEncoder.encode(dto.newPassword()));
        return new MessageDTO("Password updated successfully. Please sign in again.");
    }
}
