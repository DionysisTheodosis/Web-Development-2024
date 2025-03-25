package com.icsd.healthcare.user;

import com.icsd.healthcare.shared.validators.ValidName;
import com.icsd.healthcare.shared.validators.ValidPersonalID;
import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record UserDetailsDto(
        @ValidName
        String firstName,

        @ValidName
        String lastName,

        @Email(message = "Invalid email format")
        String email,

        @ValidPersonalID
        String personalID
) {
}