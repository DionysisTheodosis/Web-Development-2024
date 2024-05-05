package com.icsd.healthcare.user.dto;

import com.icsd.healthcare.user.entity.UserRole;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record UserSignUpDto(
        @NotBlank(message = "First name cannot be blank")
        @Pattern(regexp = "\\D*", message = "First name cannot contain digits")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Pattern(regexp = "\\D*", message = "Last name cannot contain digits")
        String lastName,

        @NotEmpty(message = "User role cannot be blank")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password cannot be blank")
        String password,

        @NotBlank(message = "Personal ID cannot be blank")
        @Pattern(regexp = "[a-zA-Z]{2}\\d{9}", message = "Invalid personal ID format")
        @Size(min = 11, max = 11, message = "Personal ID must be 11 characters long")
        String personalID,

        //@NotEmpty(message = "User role cannot be blank")
        @NotNull(message = "User role cannot be null")
        UserRole userRole
) {
}
