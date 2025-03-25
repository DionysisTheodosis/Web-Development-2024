package com.icsd.healthcare.medicalhistoryrecord;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/medical-history-records")
@Tag(name = "Medical History Records")
public class MedicalHistoryRecordController {

    private final MedicalHistoryRecordService medicalHistoryRecordService;

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/by-patient/me")
    public ResponseEntity<Set<MedicalHistoryRecordDto>> getOwnMedicalHistoryRecords() {

        return ResponseEntity.ok().body(medicalHistoryRecordService.getOwnMedicalHistoryRecords());
    }


    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/excel", consumes = {"multipart/form-data"})
    public ResponseEntity<String> addMedicalHistoryRecordsByExcel(
            @RequestPart("file") MultipartFile file,
            @RequestPart("patientId") String patientId) {

        return ResponseEntity.ok(medicalHistoryRecordService.addMedicalHistoryRecordsByExcel(file, Integer.parseInt(patientId)));
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/csv", consumes = {"multipart/form-data"})
    public ResponseEntity<String> addMedicalHistoryRecordsByCsv(
            @RequestPart("file") MultipartFile file,
            @RequestPart("patientId") String patientId) {

        return ResponseEntity.ok(medicalHistoryRecordService.addMedicalHistoryRecordsByCsv(file, Integer.parseInt(patientId)));
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    @DeleteMapping(value = "/by-patient/{patient-id}")
    public ResponseEntity<String> deleteMostRecentMedicalRecordByPatientId(@PathVariable(value = "patient-id") Integer patientId) {
        medicalHistoryRecordService.deleteMostRecentMedicalRecord(patientId);
        return ResponseEntity.ok().body("Successful Deletion");
    }

    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @DeleteMapping(value = "/by-patient/me")
    public ResponseEntity<String> deleteOwnMostRecentMedicalRecordByPatientId() {
        medicalHistoryRecordService.deleteOwnMostRecentMedicalRecord();
        return ResponseEntity.ok().body("Successful Deletion");
    }


    @PreAuthorize("hasAnyAuthority('PATIENT')")
    @GetMapping("/{medical-history-record-id}/by-patient/me")
    public ResponseEntity<MedicalHistoryRecordDto> getOwnMedicalHistoryRecordDetails(
            @PathVariable("medical-history-record-id") Integer recordId) {
        MedicalHistoryRecordDto medicalHistoryRecord = medicalHistoryRecordService.getOwnMedicalHistoryRecordById(recordId);
        return ResponseEntity.ok(medicalHistoryRecord);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/{medical-history-record-id}")
    public ResponseEntity<MedicalHistoryRecordDto> getMedicalHistoryRecordDetails(
            @PathVariable("medical-history-record-id") Integer recordId) {
        MedicalHistoryRecordDto medicalHistoryRecord = medicalHistoryRecordService.getMedicalHistoryRecordById(recordId);
        return ResponseEntity.ok(medicalHistoryRecord);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/by-patient/{patient-id}")
    public ResponseEntity<List<MedicalHistoryRecordDto>> getAllMedicalHistoryRecordsForPatient(
            @PathVariable("patient-id") Integer patientId) {
        List<MedicalHistoryRecordDto> records = medicalHistoryRecordService.getAllMedicalHistoryRecordsForPatient(patientId);
        return ResponseEntity.ok(records);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PatchMapping("/{medical-history-record-id}/by-patient/{patient-id}")
    public ResponseEntity<String> updateMedicalHistoryRecordById(
            @PathVariable("medical-history-record-id") Integer recordId,
            @PathVariable("patient-id") Integer patientId,
            @RequestBody MedicalHistoryRecordUpdateDto updateDto) {

        medicalHistoryRecordService.updateLatestMedicalHistoryRecord(recordId, patientId, updateDto);
        return ResponseEntity.ok().body("Successful Update");
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PatchMapping("/{medical-history-record-id}/by-patient/me")
    public ResponseEntity<String> updateOwnMedicalHistoryRecordById(
            @PathVariable("medical-history-record-id") Integer recordId,
            @RequestBody MedicalHistoryRecordUpdateDto updateDto) {

        medicalHistoryRecordService.updateLatestOwnMedicalHistoryRecord(recordId, updateDto);

        return ResponseEntity.ok().body("Successful Update");
    }


    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/by-patient/{patient-id}/search")
    public ResponseEntity<List<MedicalHistoryRecordDto>> searchMedicalHistoryRecords(
            @PathVariable(name = "patient-id") Integer patientId,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String identifiedIssues
    ) {
        List<MedicalHistoryRecordDto> records = medicalHistoryRecordService.searchMedicalHistoryRecords(patientId, startDate, endDate, identifiedIssues);
        return ResponseEntity.ok(records);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/by-patient/me/search")
    public ResponseEntity<List<MedicalHistoryRecordDto>> searchOwnMedicalHistoryRecords(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String identifiedIssues
    ) {
        List<MedicalHistoryRecordDto> records = medicalHistoryRecordService.searchOwnMedicalHistoryRecords(startDate, endDate, identifiedIssues);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/by-patient/{patient-id}/pageable/search")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public ResponseEntity<Page<MedicalHistoryRecordDto>> searchPageMedicalHistoryRecords(
            @PathVariable("patient-id") Integer patientId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "creationDate") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "identifiedIssues", required = false) String identifiedIssues
    ) {
        Page<MedicalHistoryRecordDto> records = medicalHistoryRecordService.searchPageMedicalHistoryRecords(
                patientId, startDate, endDate, identifiedIssues, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder.toUpperCase()), sortBy))
        );

        return ResponseEntity.status(HttpStatus.OK).body(records);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping("/by-patient/me/pageable/search")
    public ResponseEntity<Page<MedicalHistoryRecordDto>> searchOwnPageMedicalHistoryRecords(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "creationDate") String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "desc") String sortOrder,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "identifiedIssues", required = false) String identifiedIssues
    ) {
        Page<MedicalHistoryRecordDto> records = medicalHistoryRecordService.searchOwnPageMedicalHistoryRecords(
                startDate, endDate, identifiedIssues, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder.toUpperCase()), sortBy))
        );
        return ResponseEntity.status(HttpStatus.OK).body(records);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping("/with-appointment")
    public ResponseEntity<String> createMedicalHistoryRecord(@RequestBody NewMedicalHistoryRecordDto recordDto){
        medicalHistoryRecordService.createMedicalHistoryRecord(recordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }


}
