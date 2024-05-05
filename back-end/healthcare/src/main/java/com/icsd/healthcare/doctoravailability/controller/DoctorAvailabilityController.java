package com.icsd.healthcare.doctoravailability.controller;


import com.icsd.healthcare.doctor.entity.Doctor;
import com.icsd.healthcare.doctor.repository.DoctorRepository;
import com.icsd.healthcare.doctoravailability.dto.DoctorAvailabilityDto;
import com.icsd.healthcare.doctoravailability.dto.DoctorAvailabilityResponseDto;
import com.icsd.healthcare.doctoravailability.entity.DoctorAvailability;
import com.icsd.healthcare.doctoravailability.repository.DoctorAvailabilityRepository;
import com.icsd.healthcare.doctoravailability.service.DoctorAvailabilityService;
import com.icsd.healthcare.doctoravailability.service.impl.DoctorAvailabilityServiceImpl;
import com.icsd.healthcare.exceptionhandler.ErrorCode;
import com.icsd.healthcare.slot.dto.SlotSaveDto;
import com.icsd.healthcare.slot.entity.Slot;
import com.icsd.healthcare.slot.repository.SlotRepository;
import com.icsd.healthcare.user.exceptio.UserNotFoundException;
import com.icsd.healthcare.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@RestController
@RequestMapping("/api/doctorAvailability")
public class DoctorAvailabilityController {

    private final DoctorAvailabilityServiceImpl doctorAvailabilityService;
    private final SlotRepository slotRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DoctorAvailabilityRepository docAv;

    /*    @PostMapping("/saveSlot")
        public ResponseEntity<DoctorAvailabilityResponseDto> saveDoctorAvailability(@RequestBody @Valid DoctorAvailabilityDto doctorAvailabilityDto) throws ChangeSetPersister.NotFoundException {
            return ResponseEntity.status(HttpStatus.CREATED).body(doctorAvailabilityService.saveDoctorAvailability(doctorAvailabilityDto));
        }*/
    @RequestMapping("")
    public void fooMethod(HttpSession session) {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        HttpSession seAssion= attr.getRequest().getSession(true);
    }

    @PostMapping("/saveSlot/{date-time}")
    public ResponseEntity<DoctorAvailability> saveDoctorAvailabilit(HttpServletRequest request, @PathVariable("date-time") String parameter) {
        if (parameter != null) {
            Slot slot = slotRepository.save(
                    Slot.builder()
                            .slotDateTime(LocalDateTime.parse(parameter, DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                            .build()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    docAv.save(DoctorAvailability
                            .builder()
                            .doctor(
                                    doctorRepository.findByUser(
                                            userRepository.findByEmail(
                                                            request.getUserPrincipal().getName())
                                                    .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND_WITH_THIS_CREDENTIALS)
                                                    )
                                    )
                            )
                            .slot(slot)
                            .build()));
        } else {
            return null;
        }
    }
/*    @GetMapping("/{doctorId}")
    public ResponseEntity<HttpStatus> getAvailableSlotsByDoctorID(
            @RequestParam(defaultValue = "1") Integer doctorId) {


    }*/
/*@PostMapping("/importSlots")
public ResponseEntity<String> importSlots(@RequestParam("file") MultipartFile file) {
    try {
        // Parse the CSV file and save slots
        List<SlotSaveDto> slots = csvParser.parseSlotsFromFile(file);
        slotService.saveSlots(slots);
        return ResponseEntity.ok("Slots imported successfully.");
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to import slots: " + e.getMessage());
    }*/


}







