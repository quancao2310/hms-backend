package com.example.hms.patientfacingservice.auth.security.impl;

import com.example.hms.models.external.patientaccount.PatientAccount;
import com.example.hms.patientfacingservice.auth.security.ExtendedUserDetails;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

@Getter
public class CustomUserDetails implements ExtendedUserDetails {

    private final PatientAccount patientAccount;

    public CustomUserDetails(PatientAccount patientAccount) {
        this.patientAccount = patientAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return patientAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return patientAccount.getEmail();
    }

    @Override
    public UUID getId() {
        return patientAccount.getId();
    }

    @Override
    public String getEmail() {
        return patientAccount.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
