package com.icsd.healthcare.shared.security;

import com.icsd.healthcare.shared.validators.ValidAmka;
import com.icsd.healthcare.shared.validators.ValidName;
import com.icsd.healthcare.shared.validators.ValidPassword;
import com.icsd.healthcare.shared.validators.ValidPersonalID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequestDto(

        @ValidName
        String firstName,

        @ValidName
        String lastName,

        @Email(message = "Invalid email format")
        String email,

        @ValidPassword
        @NotBlank(message = "Password cannot be blank")
        String password,

        @ValidPersonalID
        String personalID,

        @ValidAmka
        String amka


) {
}
