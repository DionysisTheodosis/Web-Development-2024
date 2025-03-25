package com.icsd.healthcare.medicalhistoryrecord;

import com.icsd.healthcare.appointment.AppointmentService;
import com.icsd.healthcare.patient.PatientService;
import com.icsd.healthcare.shared.exception.EntityAlreadyExistsException;
import com.icsd.healthcare.shared.exception.InvalidFileTypeException;
import com.icsd.healthcare.shared.exception.InvalidOperationException;
import com.icsd.healthcare.shared.exception.ParsingFileIOException;
import com.icsd.healthcare.shared.utils.CsvParser;
import com.icsd.healthcare.shared.validators.GenericValidator;
import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MedicalHistoryRecordService {
    private final PatientService patientService;
    private final MedicalHistoryRecordMapper mapper;
    private final MedicalHistoryRecordRepository medicalHistoryRecordRepository;
    private final CsvParser csvParser;
    private final GenericValidator genericValidator;
    private final AppointmentService appointmentService;


    public boolean hasRecordsForPatient(Integer patientId) {
        List<MedicalHistoryRecord> records = medicalHistoryRecordRepository
                .findByMedicalHistory_Patient_PatientID(patientId);
        return !records.isEmpty();
    }

    @Transactional
    public String addMedicalHistoryRecordsByCsv(MultipartFile file, Integer patientId) {

        try {
            List<MedicalHistoryRecordFileRepresentation> recordsFromFile = csvParser
                    .parseCsv(file, MedicalHistoryRecordFileRepresentation.class);

            return processMedicalHistoryRecordFileRepresentationsByPatientId(recordsFromFile, patientId);

        } catch (IOException e) {
            log.error("Error processing file", e);
            throw new ParsingFileIOException();
        }
    }

    @Transactional
    public String addMedicalHistoryRecordsByExcel(MultipartFile file, Integer patientId) {
        validateFileType(file);

        try (InputStream inputStream = file.getInputStream()) {
            List<MedicalHistoryRecordFileRepresentation> recordsFromFile = Poiji.fromExcel(
                    inputStream, PoijiExcelType.XLSX, MedicalHistoryRecordFileRepresentation.class);
            return processMedicalHistoryRecordFileRepresentationsByPatientId(recordsFromFile, patientId);
        } catch (IOException e) {
            log.error("Error processing file", e);
            throw new ParsingFileIOException();
        }
    }

    private void validateFileType(MultipartFile file) {
        if (file == null || !"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                .equals(file.getContentType())) {
            throw new InvalidFileTypeException("Invalid file type, valid types are xlsx or xls");
        }
    }

    private String processMedicalHistoryRecordFileRepresentationsByPatientId(
            List<MedicalHistoryRecordFileRepresentation> recordsFromFile, Integer patientId
    ) {
        if (!isMedicalHistoryEmptyByPatientId(patientId)) {
            return "Patient's medical history is not empty.Medical history records import is not allowed.";
        }

        Set<NewMedicalHistoryRecordDto> recordDtos =
                mapMedicalHistoryRecordFileRepresentationToNewMedicalHistoryRecordDto(recordsFromFile, patientId);
        Set<NewMedicalHistoryRecordDto> validRecordDtos = genericValidator.validateAndFilter(recordDtos);
        int sumOfSavedRecords = saveMultipleMedicalHistoryRecords(validRecordDtos);

        if ((recordsFromFile.size() == validRecordDtos.size()) && (sumOfSavedRecords != 0)) {
            return "Successfully processed and saved all medical history records.";
        }
        if (sumOfSavedRecords != 0) {
            throw new EntityAlreadyExistsException(
                    "Could not save medical history records, because they are already exist."
            );
        }
        return "Failed to save all given medical history records. Number of records saved is: " + sumOfSavedRecords;
    }

    private boolean isMedicalHistoryEmptyByPatientId(Integer patientId) {
        return !hasRecordsForPatient(patientId);
    }

    private Set<NewMedicalHistoryRecordDto> mapMedicalHistoryRecordFileRepresentationToNewMedicalHistoryRecordDto(
            List<MedicalHistoryRecordFileRepresentation> medicalHistoryRecords, Integer id) {
        return medicalHistoryRecords.stream()
                .map(medicalHistoryRecord ->
                        mapper.mapFileRepresentationToNewDtoWithPatientId(medicalHistoryRecord, id)
                )
                .collect(Collectors.toSet());
    }

    private void saveMedicalHistoryRecord(NewMedicalHistoryRecordDto newMedicalHistoryRecord) {
        medicalHistoryRecordRepository.save(mapper.mapDtoToEntity(newMedicalHistoryRecord));
    }

    public int saveMultipleMedicalHistoryRecords(Set<NewMedicalHistoryRecordDto> records) {
        AtomicInteger sum = new AtomicInteger();
        records.forEach(recordDto -> {
            try {
                saveMedicalHistoryRecord(recordDto);
                sum.getAndIncrement();
            } catch (DataIntegrityViolationException ex) {
                log.warn("Patient {} already exist!", recordDto.toString());

            }
        });
        return sum.get();
    }

    @Transactional
    public void deleteMostRecentMedicalRecord(Integer patientId) {

        Optional<MedicalHistoryRecord> mostRecentRecordOptional = medicalHistoryRecordRepository.
                findFirstByMedicalHistory_Patient_PatientIDOrderByCreationDateDesc(patientId);

        mostRecentRecordOptional.ifPresent(medicalHistoryRecordRepository::delete);
    }

    @Transactional
    public void deleteOwnMostRecentMedicalRecord() {
        Integer patientId = patientService.getAuthenticatedPatientId();

        Optional<MedicalHistoryRecord> mostRecentRecordOptional = medicalHistoryRecordRepository.
                findFirstByMedicalHistory_Patient_PatientIDOrderByCreationDateDesc(patientId);


        medicalHistoryRecordRepository.delete(mostRecentRecordOptional.orElseThrow());

    }

    public MedicalHistoryRecordDto getMedicalHistoryRecordById(Integer recordId) {
        MedicalHistoryRecord medicalHistoryRecord = medicalHistoryRecordRepository
                .findById(recordId)
                .orElseThrow(MedicalHistoryRecordNotFoundException::new);
        return mapper.mapEntityToDto(medicalHistoryRecord);
    }

    public MedicalHistoryRecordDto getOwnMedicalHistoryRecordById(Integer recordId) {
        int patientId = patientService.getAuthenticatedPatientId();

        MedicalHistoryRecord medicalHistoryRecord = medicalHistoryRecordRepository
                .findByIdAndMedicalHistory_Patient_PatientID(recordId, patientId)
                .orElseThrow(MedicalHistoryRecordNotFoundException::new);
        return mapper.mapEntityToDto(medicalHistoryRecord);
    }


    public List<MedicalHistoryRecordDto> getAllMedicalHistoryRecordsForPatient(Integer patientId) {
        List<MedicalHistoryRecord> medicalHistoryRecords = medicalHistoryRecordRepository
                .findAllByMedicalHistory_Patient_PatientIDOrderByCreationDateDesc(patientId)
                .orElseThrow(MedicalHistoryRecordNotFoundException::new);

        return medicalHistoryRecords.stream().map(mapper::mapEntityToDto).toList();

    }


    public Set<MedicalHistoryRecordDto> getOwnMedicalHistoryRecords() {
        return patientService.getOwnMedicalHistoryRecords().stream()
                .sorted(Comparator.comparing(MedicalHistoryRecord::getCreationDate).reversed())
                .map(mapper::mapEntityToDto)
                .collect(Collectors.toSet());

    }

    @Transactional
    public void updateLatestOwnMedicalHistoryRecord(Integer recordId, MedicalHistoryRecordUpdateDto updateDto) {
        int patientId = patientService.getAuthenticatedPatientId();
        updateLatestMedicalHistoryRecord(recordId, patientId, updateDto);
    }

    @Transactional
    public void updateLatestMedicalHistoryRecord(Integer recordId, Integer patientId, MedicalHistoryRecordUpdateDto updateDto) {

        MedicalHistoryRecord latestRecord = medicalHistoryRecordRepository.
                findFirstByMedicalHistory_Patient_PatientIDOrderByCreationDateDesc(patientId).
                orElseThrow(MedicalHistoryRecordNotFoundException::new);

        if (!Objects.equals(latestRecord.getId(), recordId)) {
            throw new MedicalHistoryRecordNotFoundException("Medical history record isn't latest");
        }

        if (!latestRecord.getTreatment().equals(updateDto.treatment())) {
            latestRecord.setTreatment(updateDto.treatment());
            log.debug("Setting treatment in the medical history record");
        }

        if (!latestRecord.getIdentifiedIssues().equals(updateDto.identifiedIssues())) {
            latestRecord.setIdentifiedIssues(updateDto.identifiedIssues());
            log.debug("Setting identifiedIssue in the medical history record");
        }

        medicalHistoryRecordRepository.save(latestRecord);

    }


    public List<MedicalHistoryRecordDto> searchMedicalHistoryRecords(
            Integer patientId, LocalDate startDate, LocalDate endDate, String identifiedIssues) {

        Specification<MedicalHistoryRecord> spec = MedicalHistoryRecordSpecifications.withFilters(
                patientId, startDate, endDate, identifiedIssues);
        return medicalHistoryRecordRepository.findAll(spec).stream().map(mapper::mapEntityToDto).toList();
    }


    public List<MedicalHistoryRecordDto> searchOwnMedicalHistoryRecords(
            LocalDate startDate, LocalDate endDate, String identifiedIssues) {
        int patientId = patientService.getAuthenticatedPatientId();
        return searchMedicalHistoryRecords(patientId, startDate, endDate, identifiedIssues);
    }


    public Page<MedicalHistoryRecordDto> searchPageMedicalHistoryRecords(
            Integer patientId,
            LocalDate startDate,
            LocalDate endDate,
            String identifiedIssues,
            PageRequest pageRequest
    ) {
        Specification<MedicalHistoryRecord> spec = MedicalHistoryRecordSpecifications.withFilters(
                patientId, startDate, endDate, identifiedIssues);

        return medicalHistoryRecordRepository.findAll(spec, pageRequest)
                .map(mapper::mapEntityToDto);
    }


    public Page<MedicalHistoryRecordDto> searchOwnPageMedicalHistoryRecords(
            LocalDate startDate, LocalDate endDate, String identifiedIssues, PageRequest pageRequest) {
        int patientId = patientService.getAuthenticatedPatientId();
        return searchPageMedicalHistoryRecords(patientId, startDate, endDate, identifiedIssues, pageRequest);

    }

    @Transactional
    public void createMedicalHistoryRecord(NewMedicalHistoryRecordDto recordDto) {
        if (Boolean.TRUE.equals(appointmentService.isAppointmentAttendedOrCompleted(recordDto.appointmentId()))) {
            medicalHistoryRecordRepository.save(mapper.mapDtoToEntity(recordDto));
        } else {
            throw new InvalidOperationException("Cannot create medical history record");
        }
    }
}
