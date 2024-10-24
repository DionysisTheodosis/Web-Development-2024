package com.icsd.healthcare.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/session-cookie")
@Tag(name = "Demo")
public class DemoController {

    @Operation(
            description = "Demo Controller to get the session cookie value"

    )

    @GetMapping("")
    public ResponseEntity<String> returnSessionCookie(@CookieValue(name = "SESSION", required = false)String cookieValue) {

        return ResponseEntity.status(HttpStatus.OK).body("Session Cookie value: " + cookieValue);
    }
}

