package com.icsd.healthcare.shared.security.controller;

import com.icsd.healthcare.shared.security.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final AuthenticationManager authenticationManager;
    private final SessionRegistry sessionRegistry;
    @GetMapping("/name")
    public Optional<String> getName(Principal principal) {
        if (principal != null) {
            return Optional.ofNullable(principal.getName());
        }
        return Optional.empty(); // Return empty Optional if principal is not available
    }
    @GetMapping("/as")
    public ResponseEntity<String> searchAppointment() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        String username;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = (String) principal;
        }

        System.out.println("Username: " + username);

        return ResponseEntity.status(HttpStatus.OK).body(username);
    }
    @GetMapping("/user/roles")
    public List<String> getUserRoles() {
        List<String> roles = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                roles.add(authority.getAuthority());
            }
        }
        return roles;
    }
    @GetMapping("/active-sessions")
    public List<String> getActiveSessions() {
        return sessionRegistry.getAllPrincipals().stream()
                .map(principal -> (UserDetails) principal)
                .map(UserDetails::getUsername)
                .collect(Collectors.toList());
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

    @PostMapping("/api/login")
    public void login(
            @RequestBody LoginRequestDto loginRequest,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        System.out.println(loginRequest.username());
        System.out.println(loginRequest.password());
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                .unauthenticated(
                        loginRequest.username(), loginRequest.password()
                );
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();

        context.setAuthentication(authentication); //set context application from authentication
        securityContextHolderStrategy.setContext(context);

        securityContextRepository.saveContext(context, request, response); //save the auth context
    }

}
