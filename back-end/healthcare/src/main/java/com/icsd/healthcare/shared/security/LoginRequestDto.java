package com.icsd.healthcare.shared.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email address")
            String username,

            @NotBlank(message = "Password cannot be blank")
            String password


    ) {
    }


