package com.icsd.healthcare.doctoravailability;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/single-slot")
    public ResponseEntity<HttpStatus> saveDoctorAvailabilitySingle(
            @RequestBody @Valid DoctorAvailabilitySingleDto doctorAvailabilitySingleDto) {
            this.service.saveDoctorAvailabilitySingle(doctorAvailabilitySingleDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/multiple-slots")
    public ResponseEntity<HttpStatus> saveDoctorAvailabilityMultiple(
            @RequestBody @Valid DoctorAvailabilityMultipleDto doctorAvailabilityMultipleDto) {

        this.service.saveDoctorAvailabilityMultiple(doctorAvailabilityMultipleDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping(value = "/csv/upload", consumes = {"multipart/form-data"})
    ResponseEntity<Integer> uploadSlotsAsCsv(@RequestPart("file") MultipartFile file){

        return ResponseEntity.ok(this.service.uploadSlotsAsCsv(file));

    }

    @PostMapping(value = "/excel/upload", consumes = {"multipart/form-data"})
    ResponseEntity<Integer> uploadSlotsAsExcel(@RequestPart("file") MultipartFile file){

        return ResponseEntity.ok(this.service.uploadSlotsAsExcel(file));

    }



/*
    @GetMapping("doctor/{doctor-id}/{}")
    @GetMapping("/doctors/{doctorId}/availability")
    public ResponseEntity<Page<DoctorAvailability>> getDoctorAvailability(
            @PathVariable Integer doctorId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String date, // Optional filter
            @RequestParam(required = false) String type) // Optional filter
    )throws exception {
        // Retrieve doctor availability with optional filters and pagination
        Page<DoctorAvailability> availabilityPage = doctorAvailabilityService.getAvailability(doctorId, page, size, date, type);
        return ResponseEntity.ok(availabilityPage);
    }*/

/*    @GetMapping("/{doctorId}")
    public ResponseEntity<HttpStatus> getAvailableSlotsByDoctorID(
            @RequestParam(defaultValue = "1") Integer doctorId) {


    }*/
/*@PostMapping("/importSlots")
public ResponseEntity<String> importSlots(@RequestParam("file") MultipartFile file) {
    try {
        // Parse the CSV file and save slots
        List<SlotDto> slots = csvParser.parseSlotsFromFile(file);
        slotService.saveSlots(slots);
        return ResponseEntity.ok("Slots imported successfully.");
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to import slots: " + e.getMessage());
    }*/

   /* @GetMapping("/")
    public ResponseEntity<Optional<List<DoctorAvailability>>> getDoctorAvailability(HttpServletRequest request) {


    }*/
}







