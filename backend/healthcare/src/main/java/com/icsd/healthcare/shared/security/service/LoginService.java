package com.icsd.healthcare.shared.security.service;

import com.icsd.healthcare.shared.security.LoginRequestDto;
import com.icsd.healthcare.shared.utils.AuthUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginService.class);
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final AuthenticationManager authenticationManager;
    private final AuthUtils authUtils;
    public ResponseEntity<String> login(LoginRequestDto loginRequest,HttpServletRequest request,
                                        HttpServletResponse response) {
        try {
            System.out.println(loginRequest.username()+" paass "+loginRequest.password());
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                    .unauthenticated(
                            loginRequest.username(), loginRequest.password()
                    );
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            System.out.println("User is authenticated and he is a " +getRedirectionUrl(authentication));
            return ResponseEntity.ok(getRedirectionUrl(authentication));
        }
        catch (Exception e) {
            System.out.println("Geia na doyme ti ginetai" + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

    public String getRedirectionUrl(Authentication authentication) {

        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();
                if (null!=role) {
                    if(authentication.getPrincipal().equals("anonymousUser")){
                        return "ANONYMOUS";
                    }
                    return role;
                }
            }

        }
        throw new RuntimeException("Something Happened");
    }

    public Boolean isSignIn() {
        return authUtils.isAuthenticated();
    }

    public String getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getRedirectionUrl(authentication);
    }

}
