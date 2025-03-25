package com.icsd.healthcare.patient;

import com.icsd.healthcare.medicalhistory.MedicalHistory;
import com.icsd.healthcare.medicalhistory.MedicalHistoryNotFoundException;
import com.icsd.healthcare.medicalhistory.MedicalHistoryService;
import com.icsd.healthcare.medicalhistoryrecord.MedicalHistoryRecord;
import com.icsd.healthcare.shared.exception.InvalidSortByParameterException;
import com.icsd.healthcare.shared.exception.NoChangesDetectedException;
import com.icsd.healthcare.shared.exception.ParsingFileIOException;
import com.icsd.healthcare.shared.utils.AuthUtils;
import com.icsd.healthcare.shared.utils.CsvParser;
import com.icsd.healthcare.shared.validators.GenericValidator;
import com.icsd.healthcare.shared.validators.ValidCsvFileType;
import com.icsd.healthcare.shared.validators.ValidExcelFileType;
import com.icsd.healthcare.user.UserAlreadyExistsException;
import com.icsd.healthcare.user.UserService;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final UserService userService;
    private final CsvParser csvParser;
    private final MedicalHistoryService medicalHistoryService;
    private final GenericValidator genericValidator;
    private final AuthUtils authUtil;

    @Transactional
    public void addNewPatient(NewPatientDto newPatientDto) {
        checkIfUserAndPatientExists(newPatientDto);

        Patient patient = savePatient(patientMapper.mapDtoToEntity(newPatientDto));

        MedicalHistory medicalHistory = MedicalHistory.builder()
                .patient(patient)
                .build();

        medicalHistory = medicalHistoryService.saveMedicalHistory(medicalHistory);

        patient.setMedicalHistory(medicalHistory);

        log.info("Patient with id {} saved", patient.getPatientID());
    }

    //Delete method for patient  as Doctor or Secretary user
    @Transactional
    public void deleteGivenPatient(Integer id) {

        if (id != null) {
            Patient patient = patientRepository.findById(id).orElseThrow(PatientNotFoundException::new);
            patientRepository.delete(patient);
            log.info("Patient with id {} deleted", patient);
        }

    }

    //   Return method own patient's info
    public PatientInfoDto getPatientInfo() {
        Patient patient = getAuthenticatedPatient();
        log.info("Personal Patient info returned");
        return patientMapper.mapEntityToPatientInfoDto(patient);
    }

    //   Return method for patient's info as Doctor or Secretary user
    public PatientInfoDto getPatientInfo(int id) {

        Patient patient = patientRepository.findByPatientID(id).orElseThrow(PatientNotFoundException::new);
        log.info("Patient info returned");
        return patientMapper.mapEntityToPatientInfoDto(patient);
    }

    public Set<PatientInfoDto> getPatientsInfo() {

        return patientRepository.findAll().stream().map(patientMapper::mapEntityToPatientInfoDto).collect(Collectors.toSet());

    }

    //Return page method for patient's info as Doctor
    public Page<PatientInfoDto> getPageablePatientsInfo(int page, int sizePerPage, String sortBy, String sortOrder) {

        sortBy = modifySortBy(sortBy);

        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page, sizePerPage, Sort.by(direction, sortBy));
        Page<Patient> patientsPage = patientRepository.findAll(pageable);
        return patientsPage.map(patientMapper::mapEntityToPatientInfoDto);

    }

    @Transactional
    public Patient savePatient(Patient patient) {
        try {
            return patientRepository.save(patient);
        } catch (Exception e) {
            throw new PatientSaveException();
        }
    }

    //Update method for patient's info as the same Patient
    @Transactional
    public PatientInfoDto updateOwnPatientInfo(PatientInfoDto patientInfoDto) {
        Patient patient = getAuthenticatedPatient();
        return updatePatientInfo(patientInfoDto, patient);

    }

    //Update method for patient's info as Doctor or Secretary user
    @Transactional
    public PatientInfoDto updateGivenPatientInfo(Integer patientId, PatientInfoDto patientInfoDto) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient with id " + patientId + " not found"));
        return updatePatientInfo(patientInfoDto, patient);


    }

    private PatientInfoDto updatePatientInfo(PatientInfoDto patientInfoDto, Patient patient) {
        boolean updated = false;

        if (!patientInfoDto.firstName().equals(patient.getUser().getFirstName())) {
            patient.getUser().setFirstName(patientInfoDto.firstName());
            log.debug("Patient set for update firstname");
            updated = true;
        }

        if (!patientInfoDto.lastName().equals(patient.getUser().getLastName())) {
            patient.getUser().setLastName(patientInfoDto.lastName());
            log.debug("Patient set for update lastname");
            updated = true;
        }

        if (!patientInfoDto.email().equals(patient.getUser().getEmail())) {
            patient.getUser().setEmail(patientInfoDto.email());
            log.debug("Patient set for update email");
            updated = true;
        }

        if (!patientInfoDto.amka().equals(patient.getAmka())) {
            patient.setAmka(patientInfoDto.amka());
            log.debug("Patient set for update amka");
            updated = true;
        }

        if (!patientInfoDto.personalID().equals(patient.getUser().getPersonalID())) {
            patient.getUser().setPersonalID(patientInfoDto.personalID());
            log.debug("Patient set for update personalID");
            updated = true;
        }

        if (updated) {

            return patientMapper.mapEntityToPatientInfoDto(patientRepository.save(patient));

        }

        throw new NoChangesDetectedException("No changes detected for patient information to be update");
    }

    public Page<PatientInfoDto> searchPatients(String lastName, int page, int sizePerPage, String sortBy, String sortOrder) {
        sortBy = modifySortBy(sortBy);
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page, sizePerPage, Sort.by(direction, sortBy));
        if (lastName != null && !lastName.isEmpty()) {
            Page<Patient> patientsPage = patientRepository.findByUser_lastNameStartingWithIgnoreCase(lastName, pageable);
            return patientsPage.map(patientMapper::mapEntityToPatientInfoDto);
        } else {
            return Page.empty();
        }
    }

    public PatientInfoDto searchPatientByAmka(String amka) {
        return patientMapper.mapEntityToPatientInfoDto(patientRepository.findByAmka(amka).orElseThrow(PatientNotFoundException::new));
    }

    public boolean existsByAmka(String amka) {
        return patientRepository.existsByAmka(amka);
    }

    private void checkIfUserAndPatientExists(NewPatientDto newPatientDto) {
        boolean isUserExists = userService.isUserExistsByPersonalIDAndEmail(newPatientDto.personalID(), newPatientDto.email());
        log.warn(String.valueOf(isUserExists));
        if (isUserExists) {
            throw new UserAlreadyExistsException();
        }
        if (existsByAmka(newPatientDto.amka())) {
            throw new PatientAlreadyExistsException();
        }
    }

    private String modifySortBy(String sortBy) {
        if (sortBy.equals("firstName") || sortBy.equals("lastName") || sortBy.equals("email") || sortBy.equals("personalID")) {
            sortBy = "User_" + sortBy;
        } else if (!sortBy.equals("patientID") && !sortBy.equals("amka") && !sortBy.equals("registrationDate")) {
            throw new InvalidSortByParameterException();
        }
        return sortBy;
    }

    public String addPatientsByCsv(@ValidCsvFileType MultipartFile file) {
        try {

            List<PatientFileRepresentation> patientsFromFile = csvParser.parseCsv(file, PatientFileRepresentation.class);

            return processPatientFileRepresentations(patientsFromFile);

        } catch (IOException e) {
            log.error("Error processing file ", e);
            throw new ParsingFileIOException();
        }

    }

    private Set<NewPatientDto> mapPatientFileRepresentationToNewPatientDto(List<PatientFileRepresentation> patients) {

        return patients.stream()
                .map(patientMapper::mapPatientFileRepresentationToNewPatientDto)
                .collect(Collectors.toSet());

    }


    public String addPatientsByExcel(@ValidExcelFileType MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {

            List<PatientFileRepresentation> patientsFromFile = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, PatientFileRepresentation.class);

            return processPatientFileRepresentations(patientsFromFile);

        } catch (IOException e) {
            log.error("Error processing file", e);
            throw new ParsingFileIOException();
        }

    }

    private String processPatientFileRepresentations(List<PatientFileRepresentation> patientsFromFile) {
        Set<NewPatientDto> patientDtos = mapPatientFileRepresentationToNewPatientDto(patientsFromFile);
        Set<NewPatientDto> validPatientDtos = genericValidator.validateAndFilter(patientDtos);
        int sumOfSavedPatients = saveMultiplePatients(validPatientDtos);

        if (patientsFromFile.size() == validPatientDtos.size()) {
            return "Successfully processed and saved " + sumOfSavedPatients + " patients.";
        }
        if (sumOfSavedPatients == 0) {
            throw new PatientAlreadyExistsException("Couldn't save patients because they already exist. ");
        }

        return "Failed to save all given patients. Number of patients saved is: " + sumOfSavedPatients;
    }

    public void savePatient(NewPatientDto patientDto) {
        patientRepository.save(patientMapper.mapDtoToEntity(patientDto));
    }

    public int saveMultiplePatients(Set<NewPatientDto> patients) {
        AtomicInteger sum = new AtomicInteger();
        patients.forEach(patientDto -> {
            try {
                savePatient(patientDto);
                sum.getAndIncrement();
            } catch (DataIntegrityViolationException ex) {
                log.warn("Patient {} already exist!", patientDto.toString());
            }
        });
        return sum.get();
    }

    public Patient getPatientById(int patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));
    }

    public MedicalHistory getMedicalHistoryForPatient(Integer patientId) {
        Patient patient = getPatientById(patientId);
        return patient.getMedicalHistory();
    }

    public Set<MedicalHistoryRecord> getOwnMedicalHistoryRecords() {

        Patient patient = getAuthenticatedPatient();
        log.warn("PATIENT{}", patient.toString());
        MedicalHistory medicalHistory = patient.getMedicalHistory();

        if (medicalHistory == null) {
            throw new MedicalHistoryNotFoundException("Medical history not found for patient with ID: " + patient.getPatientID());
        }

        return medicalHistory.getMedicalHistoryRecords();
    }

    public Patient findPatientById(int patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + patientId));
    }

    public Patient getAuthenticatedPatient() {
        String email = authUtil.getAuthenticatedUserEmail();
        return patientRepository.findByUser_Email(email).orElseThrow(PatientNotFoundException::new);
    }

    public Integer getAuthenticatedPatientId() {
        return getAuthenticatedPatient().getPatientID();
    }

    public Page<PatientInfoDto> searchPatients2(String lastName, String amka, int page, int sizePerPage, String sortBy, String sortOrder) {
        if (lastName != null && amka != null) {
           return this.searchByLastNameAndAmka(lastName, amka, page, sizePerPage, sortBy,sortOrder);
        } else if (lastName != null) {
           return this.searchPatients(lastName, page, sizePerPage, sortBy,sortOrder);
        } else if (amka != null) {
            return  this.searchPatientByAmkaContaining(amka,page, sizePerPage, sortBy,sortOrder);
        } else {
             return this.getPageablePatientsInfo(page,sizePerPage,sortBy,sortOrder);
        }
    }

    private Page<PatientInfoDto> searchByLastNameAndAmka(String lastName,String amka, int page, int sizePerPage, String sortBy, String sortOrder) {
        sortBy = modifySortBy(sortBy);
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page, sizePerPage, Sort.by(direction, sortBy));
        if (amka != null && !amka.isEmpty() && lastName != null && !lastName.isEmpty()) {
            Page<Patient> patientsPage = patientRepository.findByUser_lastNameStartingWithIgnoreCaseAndAmkaStartingWith(lastName,amka,pageable);
            return patientsPage.map(patientMapper::mapEntityToPatientInfoDto);
        } else {
            return Page.empty();
        }
    }


    private Page<PatientInfoDto> searchPatientByAmkaContaining(String amka, int page, int sizePerPage, String sortBy, String sortOrder) {
        sortBy = modifySortBy(sortBy);
        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page, sizePerPage, Sort.by(direction, sortBy));
        if (amka != null && !amka.isEmpty()) {
            Page<Patient> patientsPage = patientRepository.findByAmkaStartingWith(amka,pageable);
            return patientsPage.map(patientMapper::mapEntityToPatientInfoDto);
        } else {
            return Page.empty();
        }
    }
}
