package com.icsd.healthcare.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    SECRETARY,
    DOCTOR,
    PATIENT;

    @Override
    public String getAuthority() {
        return this.name();
    }
}