package com.icsd.healthcare.secretary;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name="Secretary")
@RestController
@RequestMapping("/api/v1/secretary")
public class SecretaryController {
    private final SecretaryService secretaryService;

}
