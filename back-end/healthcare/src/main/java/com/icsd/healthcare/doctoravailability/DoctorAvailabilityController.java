package com.icsd.healthcare.doctoravailability;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/doctor-availability")
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService service;

    @PostMapping("/single-slot")
    public ResponseEntity<HttpStatus> saveDoctorAvailabilitySingle(
            HttpServletRequest request, @RequestBody @Valid DoctorAvailabilitySingleDto doctorAvailabilitySingleDto) {
            this.service.saveDoctorAvailabilitySingle(request, doctorAvailabilitySingleDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/multiple-slots")
    public ResponseEntity<HttpStatus> saveDoctorAvailabilityMultiple(
            HttpServletRequest request, @RequestBody @Valid DoctorAvailabilityMultipleDto doctorAvailabilityMultipleDto) {

        this.service.saveDoctorAvailabilityMultiple(request, doctorAvailabilityMultipleDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping(value = "/csv/upload", consumes = {"multipart/form-data"})
    ResponseEntity<Integer> uploadSlotsAsCsv(HttpServletRequest request,@RequestPart("file") MultipartFile file){

        return ResponseEntity.ok(this.service.uploadSlotsAsCsv(request,file));

    }

    @PostMapping(value = "/excel/upload", consumes = {"multipart/form-data"})
    ResponseEntity<Integer> uploadSlotsAsExcel(HttpServletRequest request,@RequestPart("file") MultipartFile file){

        return ResponseEntity.ok(this.service.uploadSlotsAsExcel(request,file));

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
    )throws Exception {
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


}







