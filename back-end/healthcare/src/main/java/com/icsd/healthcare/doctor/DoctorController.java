package com.icsd.healthcare.doctor;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "Doctor")
public class DoctorController {

    @GetMapping("/authorize")
    public ResponseEntity<String> authorize(@RequestParam String username, @RequestParam String file) {
        // Implement your authorization logic here
        if (isAuthorized(username, file)) {
            return ResponseEntity.ok("Authorized");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
        }
    }

    private boolean isAuthorized(String username, String file) {

        return true; // Placeholder logic, replace with your actual authorization logic
    }
}
