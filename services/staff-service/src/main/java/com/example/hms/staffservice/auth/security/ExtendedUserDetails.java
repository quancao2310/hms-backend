package com.example.hms.staffservice.auth.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface ExtendedUserDetails extends UserDetails {
    UUID getId();
    String getEmail();
}
