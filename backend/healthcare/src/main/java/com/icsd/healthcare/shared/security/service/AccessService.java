package com.icsd.healthcare.shared.security.service;

import com.icsd.healthcare.shared.utils.AuthUtils;
import com.icsd.healthcare.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccessService {
    private final AuthUtils authUtils;

    public Boolean validateLoggedInDoctor() {
        return UserRole.DOCTOR.equals(authUtils.getAuthenticatedUserRole());
    }
    public Boolean validateLoggedInSecretary() {
        return UserRole.SECRETARY.equals(authUtils.getAuthenticatedUserRole());
    }
    public Boolean validateLoggedInPatient() {
        return UserRole.PATIENT.equals(authUtils.getAuthenticatedUserRole());
    }

}


