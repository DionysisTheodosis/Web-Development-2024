package com.icsd.healthcare.user;

import com.icsd.healthcare.shared.validators.ValidAmka;
import com.icsd.healthcare.shared.validators.ValidName;
import com.icsd.healthcare.shared.validators.ValidPersonalID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserDto(

        @ValidName
        String firstName,

        @ValidName
        String lastName,

        @Email(message = "Invalid email format")
        String email,

        //@ValidPassword TODO TO UNCOMENT THE PASSWORD ANNOTATION
        @NotBlank(message = "Password cannot be blank")
        String password,


        @ValidPersonalID
        String personalID,

        @ValidAmka
        String amka
) {
}
