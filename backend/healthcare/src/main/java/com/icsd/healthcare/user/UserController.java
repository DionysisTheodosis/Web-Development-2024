package com.icsd.healthcare.user;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('DOCTOR','PATIENT','SECRETARY')")
    @PostMapping("/me/change-password")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {
        this.userService.changePassword(changePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','PATIENT','SECRETARY')")
    @GetMapping("/me/account-details")
    public ResponseEntity<UserDetailsDto> getOwnUserDetails() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getOwnUserDetails());
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','PATIENT','SECRETARY')")
    @PatchMapping("/me/account-details")
    public ResponseEntity<UserDetailsDto> updateOwnUserDetails(@RequestBody @Valid UserDetailsDto userDetailsDto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.changeOwnUserDetails(userDetailsDto));
    }
}
