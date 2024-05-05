package com.icsd.healthcare.user.dto;


import com.icsd.healthcare.user.entity.UserRole;

public record UserDto(
        Integer id,
        String firstName,
        String lastName,
        String email,
        UserRole userRole
) {
}
