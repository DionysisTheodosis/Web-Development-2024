package com.icsd.healthcare.doctoravailability;

import com.icsd.healthcare.shared.validators.ValidCsvFileType;
import com.icsd.healthcare.shared.validators.ValidExcelFileType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/doctor-availability")
@Tag(name = "Doctor-Availability")
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService service;

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/slot")
    public ResponseEntity<HttpStatus> saveDoctorAvailabilitySingle(
            @RequestBody @Valid NewDoctorAvailabilityDto doctorAvailabilityDto) {
        this.service.saveDoctorAvailabilitySingle(doctorAvailabilityDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/csv/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadSlotsAsCsv(
            @ValidCsvFileType @RequestPart("file") MultipartFile file) {
        System.out.println("inside uploadSlotsAsCsv");
        this.service.uploadSlotsFromFile(file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("CSV file uploaded and processed successfully.");
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/excel/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadSlotsAsExcel(
            @ValidExcelFileType @RequestPart("file") MultipartFile file) {

        this.service.uploadSlotsFromFile(file);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Excel file uploaded and processed successfully.");
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @DeleteMapping(value = "/by-slot-id/{slot-id}")
    ResponseEntity<HttpStatus> deleteDoctorAvailabilityBySlotId(@PathVariable("slot-id") Integer slotId) {
        this.service.deleteDoctorAvailabilityBySlotId(slotId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PreAuthorize("hasAuthority('DOCTOR')")
    @PatchMapping("")
    ResponseEntity<HttpStatus> updateDoctorAvailability(
            @RequestBody @Valid DoctorAvailabilitySingleDto doctorAvailabilitySingleDto) {

        this.service.updateDoctorAvailability(doctorAvailabilitySingleDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR','PATIENT')")
    @GetMapping("/search")
    public ResponseEntity<DoctorAvailabilityMultipleDto> getDoctorAvailabilityByDate(
            @RequestParam(required = false)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        DoctorAvailabilityMultipleDto doctorAvailabilityMultipleDto = this.service.
                getDoctorAvailabilitiesByDate(date);
        return ResponseEntity.ok(doctorAvailabilityMultipleDto);
    }

}







