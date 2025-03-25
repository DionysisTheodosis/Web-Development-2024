package com.icsd.healthcare.patient;

import com.icsd.healthcare.medicalhistoryrecord.MedicalHistoryRecordService;
import com.icsd.healthcare.shared.validators.ValidAmka;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final PatientRepository repository;

    @Operation(
            description = "Creation of single patient from Doctor,with authority only of doctor",
            summary = "Post endpoint to create new patient"
    )
    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    @PostMapping("")
    public ResponseEntity<String> addGivenPatient(@RequestBody @Valid NewPatientDto newPatientDto) {
        patientService.addNewPatient(newPatientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Patient added");
    }

    @Operation(
            description = """
                    Post endpoint to insert new patients  by providing the csv file with patients
                    and this can only been accessed from doctor.
                    
                    The Csv file will contain the following columns in this exact name and order :
                      - `FirstName`: Patients first name.
                      - `LastName`: Patients last name.
                      - `Email`: Patients unique email address.
                      - `Password`: Password for patients account.
                      - `PersonalID`: Patients unique personal id.
                      - `AMKA`: Patients unique AMKA.""",
            summary = "Post endpoint to create new patients from csv file"
    )
    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/csv", consumes = {"multipart/form-data"})
    ResponseEntity<String> addPatientsByCsv(@RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(patientService.addPatientsByCsv(file));

    }

    @Operation(
            description = """
                    Post endpoint to insert new patients  by providing the excel file with patients
                    and this can only been accessed from doctor.
                    
                    The Excel file will contain the following columns in this exact name and order :
                      - `FirstName`: Patients first name.
                      - `LastName`: Patients last name.
                      - `Email`: Patients unique email address.*
                      - `Password`: Password for patients account.
                      - `PersonalID`: Patients unique personal id.
                      - `AMKA`: Patients unique AMKA.""",
            summary = "Post endpoint to create new patients from excel file"
    )
    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/excel", consumes = {"multipart/form-data"})
    ResponseEntity<String> addPatientsByExcel(@RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(patientService.addPatientsByExcel(file));

    }

    @Operation(
            description = "Get endpoint to get the logged in patient's info, this is accessed only from patient",
            summary = "Endpoint to get logged in patient info"
    )
    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/me")
    public ResponseEntity<PatientInfoDto> getOwnPatientInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientInfo());
    }

    @Operation(
            description = "Get endpoint to return specific patient's info by his id, for this only the" +
                    " doctor and the secretary have the authority",
            summary = "Get endpoint to get patient info by id"
    )
    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<PatientInfoDto> getGivenPatientInfo(@PathVariable Integer id) {

        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientInfo(id));
    }


    @Operation(
            description = "Get endpoint to get all patients and their info, this is only authorized for doctor and secretary",
            summary = "Get endpoint to get all patients and their info"
    )
    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    @GetMapping("")
    public ResponseEntity<Set<PatientInfoDto>> getAllPatientsInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatientsInfo());
    }
    @GetMapping("/pageable/search")
    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    public ResponseEntity<Page<PatientInfoDto>> searchPatients(
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String amka,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int sizePerPage,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder) {

        Page<PatientInfoDto> patientsPage = patientService.searchPatients2(lastName,amka, page, sizePerPage, sortBy, sortOrder);

        return ResponseEntity.status(HttpStatus.OK).body(patientsPage);
    }

    @Operation(
            description = "Get endpoint to get all patients and their info as pageable" +
                    " and by default is at page=0, the size of page 10,default shorting by the lastname of patient and is in descending order" +
                    ", this is only authorized for doctor and secretary",
            summary = "Get endpoint to get all patients and their info as pageable"
    )
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

    @Operation(
            description = "Get endpoint to get the patient or patients by a given lastname and return page as a result " +
                    " and by default is at page=0, the size of page 10,default shorting by the lastname of patient and is in descending order" +
                    ", this is only authorized for doctor and secretary",
            summary = "Get endpoint patients by a given lastname and their info as pageable"
    )
    @GetMapping("/pageable/search/byLastName")
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

    @Operation(
            description = "Get endpoint to a patient by his amka, this can be accessed only by doctor and secretary",
            summary = "Get endpoint to get the patient info by the amka of a patient"
    )
    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    public ResponseEntity<PatientInfoDto> searchPatientByAmka(@RequestParam @ValidAmka String amka) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.searchPatientByAmka(amka));
    }



    @Operation(
            description = "Delete endpoint to delete a patient by the id,this can be accessed only from doctor and secretary ",
            summary = "Deletion of a  patient by his id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input, such as an improperly formatted patient id."),
            @ApiResponse(responseCode = "404", description = "Patient not found."),
            @ApiResponse(responseCode = "409", description = "Any conflicts on deletion of patient."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGivenPatient(@PathVariable Integer id) {
        patientService.deleteGivenPatient(id);
        return ResponseEntity.status(HttpStatus.OK).body("Patient deleted successfully");
    }



    @Operation(
            description = "Patch endpoint to update a patient his personal info, this can be accessed only from the patient",
            summary = "Patch endpoint to update the patient his info"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient info successfully updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientInfoDto.class))),
            @ApiResponse(responseCode = "304", description = "No changes detected to modify the patient info."),
            @ApiResponse(responseCode = "400", description = "Invalid input, such as an improperly formatted patient info data."),
            @ApiResponse(responseCode = "404", description = "Patient not found."),
            @ApiResponse(responseCode = "409", description = "Any conflicts on update of patient info."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PreAuthorize("hasAuthority('PATIENT')")
    @PatchMapping("/me")
    public ResponseEntity<PatientInfoDto> updateOwnPatientInfo(@RequestBody @Valid PatientInfoDto patientInfoDto) {
        PatientInfoDto patient = patientService.updateOwnPatientInfo(patientInfoDto);
        return ResponseEntity.status(HttpStatus.OK).body(patient);
    }

    @Operation(
            description = "Patch endpoint to update a patient's info by given his id , this can be accessed only by doctor and secretary",
            summary = "Patch endpoint to update a patient's info by given his id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient info successfully updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PatientInfoDto.class))),
            @ApiResponse(responseCode = "304", description = "No changes detected to modify the patient info."),
            @ApiResponse(responseCode = "400", description = "Invalid input, such as an improperly formatted patientId, or on data format to update."),
            @ApiResponse(responseCode = "404", description = "Patient not found."),
            @ApiResponse(responseCode = "409", description = "Any conflicts on update of patient info."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PreAuthorize("hasAnyAuthority('SECRETARY','DOCTOR')")
    @PatchMapping("/{patientId}")
    public ResponseEntity<PatientInfoDto> updatePatient(@PathVariable Integer patientId, @RequestBody @Valid PatientInfoDto patientInfoDto) {
        PatientInfoDto patientDto = patientService.updateGivenPatientInfo(patientId, patientInfoDto);
        return ResponseEntity.status(HttpStatus.OK).body(patientDto);
    }

    @Operation(
            description = """
                    Post endpoint to insert a new patient history records by providing the patient id and the csv file
                    and should be not have other records before and this can only been accessed from doctor.
                    
                    The Csv file will contain the following columns in this exact name and order :
                      - `Medical History Id`: Unique identifier for the medical history record.
                      - `Treatment`: Treatment prescribed or administered.
                      - `Identified Issues`: Medical issue or diagnosis associated with the record.""",
            summary = "Post endpoint to insert a new patient history record by csv when the history is blank"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical history record successfully added.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewPatientDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input, such as an improperly formatted Csv file."),
            @ApiResponse(responseCode = "404", description = "Patient not found."),
            @ApiResponse(responseCode = "409", description = "Patient already has medical history records."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/{patientId}/medical-history-records/csv", consumes = {"multipart/form-data"})
    public ResponseEntity<String> addMedicalHistoryRecordsByCsv(
            @RequestPart("file") MultipartFile file,
            @PathVariable("patientId") Integer patientId) {

        return ResponseEntity.ok(medicalHistoryRecordService.addMedicalHistoryRecordsByCsv(file, patientId));
    }



    @Operation(
            description = """
                    Post endpoint to insert a new patient history records by providing the patient id and the excel file
                    and should be not have other records before and this can only been accessed from doctor.
                    
                    The Excel file will contain the following columns in this exact name and order :
                      - `Medical History Id`: Unique identifier for the medical history record.
                      - `Treatment`: Treatment prescribed or administered.
                      - `Identified Issues`: Medical issue or diagnosis associated with the record.""",
            summary = "Post endpoint to insert a new patient history record by excel when the history is blank"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical history record successfully added.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NewPatientDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input, such as an improperly formatted Excel file."),
            @ApiResponse(responseCode = "404", description = "Patient not found."),
            @ApiResponse(responseCode = "409", description = "Patient already has medical history records."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(value = "/{patientId}/medical-history-records/excel", consumes = {"multipart/form-data"})
    public ResponseEntity<String> addMedicalHistoryRecordsByExcel(
            @RequestPart("file") MultipartFile file,
            @PathVariable("patientId") Integer patientId) {

        return ResponseEntity.ok(medicalHistoryRecordService.addMedicalHistoryRecordsByExcel(file, patientId));
    }

}
