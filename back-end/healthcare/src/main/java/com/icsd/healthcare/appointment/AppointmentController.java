package com.icsd.healthcare.appointment;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/appointments")
@Tag(name = "Appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PreAuthorize("hasAnyAuthority('DOCTOR','PATIENT','SECRETARY')")
    @PostMapping("")
    public ResponseEntity<String> createAppointment(@RequestBody @Valid NewAppointmentDto appointmentDto) {

        return ResponseEntity.ok(appointmentService.createNewAppointment(appointmentDto));
    }

}
