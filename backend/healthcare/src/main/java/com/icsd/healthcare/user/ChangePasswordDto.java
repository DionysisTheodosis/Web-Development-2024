package com.icsd.healthcare.user;


import com.icsd.healthcare.shared.validators.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ChangePasswordDto(

        @ValidPassword
        @NotBlank(message = "Old Password cannot be blank")
        String oldPassword,

        @ValidPassword
        @NotBlank(message = "New Password cannot be blank")
        String newPassword
) {
}
