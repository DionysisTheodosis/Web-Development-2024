package com.icsd.healthcare.shared.utils;

import com.icsd.healthcare.user.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthUtils {
    public String getAuthenticatedUserEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();

    }

    public UserRole getAuthenticatedUserRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            // Assuming `org.springframework.security.core.userdetails.User` has a method to get roles
            Collection<? extends GrantedAuthority> authorities = ((org.springframework.security.core.userdetails.User) principal).getAuthorities();

            // Convert GrantedAuthorities to UserRole, assuming a single role per user for simplicity
            for (GrantedAuthority authority : authorities) {
                try {
                    return UserRole.valueOf(authority.getAuthority());
                } catch (IllegalArgumentException ex) {
                    // Log and handle unknown roles
                    throw new IllegalStateException("Invalid role: " + authority.getAuthority());
                }
            }
        }

        throw new IllegalStateException("Unable to determine user role from Principal: " + principal);
    }

    public Boolean isAuthenticated(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName()+ "Einai? "+authentication.isAuthenticated());
        return authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser");
    }
}
