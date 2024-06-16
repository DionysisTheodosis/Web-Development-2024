package com.icsd.healthcare.shared.security.service;

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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class LoginService {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final AuthenticationManager authenticationManager;
    private final SessionRegistry sessionRegistry;
    public ResponseEntity<String> login(LoginRequestDto loginRequest,HttpServletRequest request,
                                        HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                    .unauthenticated(
                            loginRequest.username(), loginRequest.password()
                    );
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);

            return ResponseEntity.ok(getRedirectionUrl(authentication));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

    public String getRedirectionUrl(Authentication authentication) {

        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();
                switch (role) {
                    case "DOCTOR":
                        return "/doctor.html";
                    case "PATIENT":
                        return "/patient.html";
                    case "SECRETARY":
                        return "/secretary.html";


                    // Add cases for other roles if needed
                }
            }

        }
        throw new RuntimeException("Something Happened");
    }
}
