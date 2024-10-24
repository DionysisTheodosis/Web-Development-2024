package com.icsd.healthcare.doctoravailability;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/doctor-availability")
@Tag(name = "Doctor-Availability")
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService service;

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/slot")
    public ResponseEntity<HttpStatus> saveDoctorAvailabilitySingle(
            @RequestBody @Valid DoctorAvailabilitySingleDto doctorAvailabilitySingleDto) {
        this.service.saveDoctorAvailabilitySingle(doctorAvailabilitySingleDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/csv/upload", consumes = {"multipart/form-data"})
    ResponseEntity<HttpStatus> uploadSlotsAsCsv(@RequestPart("file") MultipartFile file) {
        this.service.uploadSlotsAsCsv(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/excel/upload", consumes = {"multipart/form-data"})
    ResponseEntity<Integer> uploadSlotsAsExcel(@RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(this.service.uploadSlotsAsExcel(file));

    }

}







