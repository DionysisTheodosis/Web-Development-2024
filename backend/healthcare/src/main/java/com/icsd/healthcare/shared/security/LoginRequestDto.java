package com.icsd.healthcare.shared.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Schema(name = "Login")
@Builder
public record LoginRequestDto(

        @Schema(example = "stef@hotmail.com",requiredMode = REQUIRED)
        @Email(message = "Invalid email address")
        @JsonProperty(value = "email")
        String username,

        @Schema(example = "111",requiredMode = REQUIRED)
        @NotBlank(message = "Password cannot be blank")
        @JsonProperty(value = "password")
        String password


) {

}


