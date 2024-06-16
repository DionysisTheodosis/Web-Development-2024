package com.icsd.healthcare.shared.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequestDto(

        @Email(message = "Invalid email address")
        @JsonProperty(value = "email")
        String username,

        @NotBlank(message = "Password cannot be blank")
        String password


) {

}


