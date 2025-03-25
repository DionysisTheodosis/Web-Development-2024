package com.icsd.healthcare.secretary;

import com.icsd.healthcare.shared.validators.ValidAmka;
import com.icsd.healthcare.shared.validators.ValidName;
import com.icsd.healthcare.shared.validators.ValidPersonalID;
import jakarta.validation.constraints.Email;
import lombok.Builder;
@Builder
public record SecretaryInfoDto(


        Integer id,

        @ValidName
        String firstName,

        @ValidName
        String lastName,

        @Email(message = "Invalid email format")
        String email,

        @ValidPersonalID
        String personalID,

        @ValidAmka
        String amka


) {}