package com.icsd.healthcare.doctor;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Doctor")
@RequestMapping("/api/v1/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("")
    public ResponseEntity<DoctorInfoDto> getDoctor() {
        return ResponseEntity.ok().body(doctorService.getDoctorInfo());
    }

    @PostMapping("")
    public ResponseEntity<DoctorInfoDto> updateDoctorInfo(@RequestBody @Valid DoctorInfoDto doctorInfoDto) {
        return ResponseEntity.ok().body(doctorService.updateDoctorInfo(doctorInfoDto));
    }
}
