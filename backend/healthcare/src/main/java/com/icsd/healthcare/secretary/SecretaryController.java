package com.icsd.healthcare.secretary;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@Tag(name="Secretary")
@RestController
@PreAuthorize("hasAuthority('DOCTOR')")
@RequestMapping("/api/v1/secretary")
public class SecretaryController {
    private final SecretaryService secretaryService;

    @PostMapping("")
    public ResponseEntity<SecretaryInfoDto> createSecretary(@RequestBody @Valid SecretaryDto secretaryDto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(this.secretaryService.createSecretary(secretaryDto));
    }

    @GetMapping("/{secretary-id}")
    public ResponseEntity<SecretaryInfoDto> getSecretary(@PathVariable("secretary-id") Integer secretaryId){
        return  ResponseEntity.status(HttpStatus.OK).body(this.secretaryService.getSecretary(secretaryId));
    }

    @GetMapping("")
    public ResponseEntity<Set<SecretaryInfoDto>> getSecretaries(){
        return ResponseEntity.status(HttpStatus.OK).body(this.secretaryService.getSecretaries());
    }


}
