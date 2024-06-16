package com.icsd.healthcare.patient;

import com.icsd.healthcare.medicalhistoryrecord.MedicalHistoryRecordService;
import com.icsd.healthcare.shared.validators.ValidAmka;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/patients")
@Tag(name = "Patient")
public class PatientController {
    private final MedicalHistoryRecordService medicalHistoryRecordService;
    private final PatientService patientService;

    @Operation(
            description = "Post endpoint to create new patient",
            summary = "Creation of patient from Doctor"
    )
    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    @PostMapping("")
    public ResponseEntity<String> addGivenPatient(@RequestBody @Valid NewPatientDto newPatientDto) {
        patientService.addNewPatient(newPatientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patient added");
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/csv", consumes = {"multipart/form-data"})
    ResponseEntity<String> addPatientsByCsv(@RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(patientService.addPatientsByCsv(file));

    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/excel", consumes = {"multipart/form-data"})
    ResponseEntity<String> addPatientsByExcel(@RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(patientService.addPatientsByExcel(file));

    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/me")
    public ResponseEntity<PatientInfoDto> getOwnPatientInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientInfo());
    }

    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<PatientInfoDto> getGivenPatientInfo(@PathVariable Integer id) {

        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientInfo(id));
    }


    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    @GetMapping("")
    public ResponseEntity<Set<PatientInfoDto>> getAllPatientsInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientsInfo());
    }

    @GetMapping("/pageable")
    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    public ResponseEntity<Page<PatientInfoDto>> getPageableAllPatientsInfo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int sizePerPage,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {

        Page<PatientInfoDto> patientsPage = patientService.getPageablePatientsInfo(page, sizePerPage, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(patientsPage);
    }

    @GetMapping("/pageable/search")
    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    public ResponseEntity<Page<PatientInfoDto>> searchPatients(
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int sizePerPage,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {

        Page<PatientInfoDto> patientsPage = patientService.searchPatients(lastName, page, sizePerPage, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(patientsPage);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    public ResponseEntity<PatientInfoDto> searchPatientByAmka(@RequestParam @ValidAmka String amka) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.searchPatientByAmka(amka));
    }


    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGivenPatient(@PathVariable Integer id) {
        patientService.deleteGivenPatient(id);
        return ResponseEntity.status(HttpStatus.OK).body("Patient deleted successfully");
    }


    @PreAuthorize("hasAuthority('PATIENT')")
    @PatchMapping("/me")
    public ResponseEntity<PatientInfoDto> updateOwnPatientInfo(@RequestBody @Valid PatientInfoDto patientInfoDto) {
        PatientInfoDto patient = patientService.updateOwnPatientInfo(patientInfoDto);
        return ResponseEntity.status(HttpStatus.OK).body(patient);
    }

    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    @PatchMapping("/{patientId}")
    public ResponseEntity<PatientInfoDto> updatePatient(@PathVariable Integer patientId, @RequestBody @Valid PatientInfoDto patientInfoDto) {
        PatientInfoDto patientDto = patientService.updateGivenPatientInfo(patientId, patientInfoDto);
        return ResponseEntity.status(HttpStatus.OK).body(patientDto);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/{patientId}/medical-history-records/csv", consumes = {"multipart/form-data"})
    public ResponseEntity<String> addMedicalHistoryRecordsByCsv(
            @RequestPart("file") MultipartFile file,
            @PathVariable("patientId") Integer patientId) {

        return ResponseEntity.ok(medicalHistoryRecordService.addMedicalHistoryRecordsByCsv(file, patientId));
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/{patientId}/medical-history-records/excel", consumes = {"multipart/form-data"})
    public ResponseEntity<String> addMedicalHistoryRecordsByExcel(
            @RequestPart("file") MultipartFile file,
            @PathVariable("patientId") Integer patientId) {

        return ResponseEntity.ok(medicalHistoryRecordService.addMedicalHistoryRecordsByExcel(file, patientId));
    }

}
