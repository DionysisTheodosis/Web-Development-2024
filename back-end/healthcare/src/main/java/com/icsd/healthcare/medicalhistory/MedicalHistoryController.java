package com.icsd.healthcare.medicalhistory;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/patients")
@Tag(name = "Medical History")
public class MedicalHistoryController {
    private final MedicalHistoryService medicalHistoryService;

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/me/medical-history")
    public ResponseEntity<Integer> getOwnMedicalHistory() {

        return ResponseEntity.status(HttpStatus.OK).body(medicalHistoryService.getMedicalHistoryID());
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/{patientId}/medical-history")
    public ResponseEntity<Integer> getGivenPatientsMedicalHistory(@PathVariable Integer patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(medicalHistoryService.getMedicalHistoryID(patientId));
    }



}
