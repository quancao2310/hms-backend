package com.example.hms.staffservice.auth.security.impl;

import com.example.hms.enums.WorkingStatus;
import com.example.hms.models.internal.staff.Staff;
import com.example.hms.staffservice.auth.security.ExtendedUserDetails;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
public class CustomUserDetails implements ExtendedUserDetails {

    private final Staff staff;

    public CustomUserDetails(Staff staff) {
        this.staff = staff;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + staff.getRole())
        );
    }

    @Override
    public String getPassword() {
        return staff.getPassword();
    }

    @Override
    public String getUsername() {
        return staff.getEmail();
    }

    @Override
    public UUID getId() {
        return staff.getId();
    }

    @Override
    public String getEmail() {
        return staff.getEmail();
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
        return WorkingStatus.ACTIVE.equals(staff.getStatus());
    }
}
