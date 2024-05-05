package com.icsd.healthcare.patient.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PatientDto(
        Integer patientID,

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

        String amka,

        LocalDate registrationDate
)  {
}
