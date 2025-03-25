package com.icsd.healthcare.appointment;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/appointments")
@Tag(name = "Appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PreAuthorize("hasAnyAuthority('DOCTOR','PATIENT','SECRETARY')")
    @PostMapping("/with-date-time")
    public ResponseEntity<HttpStatus> createAppointmentByDateTime(@RequestBody @Valid NewAppointmentByDateTimeDto appointmentDto) {
        this.appointmentService.createAppointmentByDateTime(appointmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','PATIENT','SECRETARY')")
    @PostMapping("with-slot")
    public ResponseEntity<HttpStatus> createAppointmentBySlot(@RequestBody @Valid NewAppointmentBySlotDto appointmentDto) {
        this.appointmentService.createAppointmentBySlot(appointmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','PATIENT','SECRETARY')")
    @PatchMapping("{id}/cancel")
    public ResponseEntity<HttpStatus> cancelAppointment(@PathVariable Integer id ) {
        this.appointmentService.cancelAppointment(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','SECRETARY','PATIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointment(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.appointmentService.getAppointmentDto(id));
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','SECRETARY')")
    @PatchMapping("/doctor-secretary")
    public ResponseEntity<HttpStatus> updateAppointmentByDoctorOrSecretary(@RequestBody DoctorOrSecretaryUpdateAppointmentDto dto ) {
        this.appointmentService.updateAppointmentByDoctorOrSecretary(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @PatchMapping("patient")
    public ResponseEntity<HttpStatus> updateAppointmentByPatient(@RequestBody @Valid PatientUpdateAppointmentDto dto ) {
        this.appointmentService.updateAppointmentByPatient(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','SECRETARY')")
    @GetMapping("/pageable/search")
    public ResponseEntity<Page<AppointmentDto>> searchPageAppointments(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "date") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
            @RequestParam(value = "patientsLastname", required = false ) String patientsLastname,
            @RequestParam(value = "patientsAmka", required = false ) String patientsAmka,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "status",required = false) Status status
    ){
        Page<AppointmentDto> appointmentPage = this.appointmentService.searchAppointments(
                patientsLastname,
                patientsAmka,
                status,
                startDate,
                endDate,
                PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder.toUpperCase()), sortBy))
        );
        return ResponseEntity.status(HttpStatus.OK).body(appointmentPage);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/my/pageable/search")
    public ResponseEntity<Page<AppointmentDto>> searchPageAppointments(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "date") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "status",required = false) Status status
    ){
        Page<AppointmentDto> appointmentPage = this.appointmentService.searchOwnPatientsAppointments(
                status,
                startDate,
                endDate,
                PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder.toUpperCase()), sortBy))
        );
        return ResponseEntity.status(HttpStatus.OK).body(appointmentPage);
    }

}
