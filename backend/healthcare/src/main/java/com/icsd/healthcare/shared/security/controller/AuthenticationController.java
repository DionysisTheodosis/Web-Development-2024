package com.icsd.healthcare.shared.security.controller;

import com.icsd.healthcare.shared.security.LoginRequestDto;
import com.icsd.healthcare.shared.security.RegisterRequestDto;
import com.icsd.healthcare.shared.security.service.AccessService;
import com.icsd.healthcare.shared.security.service.LoginService;
import com.icsd.healthcare.shared.security.service.RegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication")
public class AuthenticationController {
    private final LoginService loginService;
    private final RegisterService registerService;
    private final AccessService accessService;


    @GetMapping("/valid/doctor")
    public ResponseEntity<Boolean> validDoctor() {
        return ResponseEntity.accepted().body(accessService.validateLoggedInDoctor());
    }
    @GetMapping("/valid/secretary")
    public ResponseEntity<Boolean> validSecretary() {
        return ResponseEntity.accepted().body(accessService.validateLoggedInSecretary());

    }
    @GetMapping("/valid/patient")
    public ResponseEntity<Boolean> validPatient() {
        return ResponseEntity.accepted().body(accessService.validateLoggedInPatient());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody @Valid LoginRequestDto loginRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
       return loginService.login(loginRequest, request, response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDto registerRequest) {
        registerService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/isSignIn")
    public ResponseEntity<Boolean> isSignIn() {
        return ResponseEntity.ok().body(loginService.isSignIn());
    }

    @GetMapping("/user-role")
    public ResponseEntity<String> getUserRole() {
        return ResponseEntity.ok().body(loginService.getUserRole());
    }

}
