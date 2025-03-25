package com.icsd.healthcare.shared.security.service;

import com.icsd.healthcare.patient.*;
import com.icsd.healthcare.shared.security.RegisterRequestDto;
import com.icsd.healthcare.user.User;
import com.icsd.healthcare.user.UserAlreadyExistsException;
import com.icsd.healthcare.user.UserRole;
import com.icsd.healthcare.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegisterService {
    private final PatientService patientService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Transactional
    public void register(RegisterRequestDto registerRequest) {
        boolean isUserExists= userService.isUserExistsByPersonalIDAndEmail(registerRequest.personalID(),registerRequest.email());
        log.warn(String.valueOf(isUserExists));
        if(isUserExists) {
            throw new UserAlreadyExistsException();
        }

        createPatient(registerRequest,createUser(registerRequest));

    }

    private User createUser(RegisterRequestDto registerRequest) {
        return User.builder()
                .firstName(registerRequest.firstName())
                .lastName(registerRequest.lastName())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .personalID(registerRequest.personalID())
                .userRole(UserRole.PATIENT)
                .build();
    }

    private void createPatient(RegisterRequestDto loginRequest, User user) {

        Patient patient = Patient.builder()
                .user(user)
                .amka(loginRequest.amka())
                .build();

        patientService.savePatient(patient);
    }


}