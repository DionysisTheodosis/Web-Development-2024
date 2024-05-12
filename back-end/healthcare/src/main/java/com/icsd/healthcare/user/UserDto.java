package com.icsd.healthcare.user;


public record UserDto(
        Integer id,
        String firstName,
        String lastName,
        String email,
        UserRole userRole
) {
}
