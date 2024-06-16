package com.icsd.healthcare.shared.security.controller;

import com.icsd.healthcare.shared.security.LoginRequestDto;
import com.icsd.healthcare.shared.security.RegisterRequestDto;
import com.icsd.healthcare.shared.security.service.LoginService;
import com.icsd.healthcare.shared.security.service.RegisterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication")
public class AuthenticationController {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final AuthenticationManager authenticationManager;
    private final SessionRegistry sessionRegistry;
    private final LoginService loginService;
    private final RegisterService registerService;

    @GetMapping("/name")
    public Optional<String> getName(Principal principal) {
        if (principal != null) {
            return Optional.ofNullable(principal.getName());
        }
        return Optional.empty(); // Return empty Optional if principal is not available
    }

    @GetMapping("/valid/doctor")
    public ResponseEntity<Boolean> validDoctor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("DOCTOR"))) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
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

}
