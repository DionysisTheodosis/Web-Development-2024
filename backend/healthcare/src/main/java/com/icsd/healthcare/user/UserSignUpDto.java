package com.icsd.healthcare.user;

import com.icsd.healthcare.shared.validators.ValidName;
import com.icsd.healthcare.shared.validators.ValidPatientRole;
import com.icsd.healthcare.shared.validators.ValidPersonalID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserSignUpDto(

        @ValidName
        String firstName,

        @ValidName
        String lastName,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        //@ValidPassword TODO TO UNCOMMENT THE VALIDPASSWORD ANNOTATION
        @NotBlank(message = "Password cannot be blank")
        String password,

        @ValidPersonalID
        String personalID,

        @ValidPatientRole
        UserRole userRole
) {
}
