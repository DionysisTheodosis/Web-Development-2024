package com.icsd.healthcare.patient;


import com.icsd.healthcare.shared.validators.ValidAmka;
import com.icsd.healthcare.shared.validators.ValidName;
import com.icsd.healthcare.shared.validators.ValidPersonalID;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
@Schema(name = "Patient")
public record NewPatientDto(

        @ValidName
        @Schema(example = "Dionysis",requiredMode = REQUIRED)
        String firstName,

        @ValidName
        @Schema(example = "Theodosis",requiredMode = REQUIRED)
        String lastName,

        @Email(message = "Invalid email format")
        @Schema(example = "email@hotmail.com",requiredMode = REQUIRED)
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Schema(example = "!A23basd",requiredMode = REQUIRED)
        //@ValidPassword TODO UNCOMENT THE VALIDPASSWORD ANNOTATION
        String password,

        @ValidPersonalID
        @Schema(example = "AT123456",requiredMode = REQUIRED)
        String personalID,

        @ValidAmka
        @Schema(example = "01234567891",requiredMode = REQUIRED)
        String amka

) {
}
