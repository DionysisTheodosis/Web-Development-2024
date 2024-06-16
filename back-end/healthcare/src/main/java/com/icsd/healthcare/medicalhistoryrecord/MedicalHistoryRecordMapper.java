package com.icsd.healthcare.medicalhistoryrecord;

import com.icsd.healthcare.medicalhistory.MedicalHistory;
import com.icsd.healthcare.medicalhistory.MedicalHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class MedicalHistoryRecordMapper{
    private final MedicalHistoryService medicalHistoryService;


    public MedicalHistoryRecord mapDtoToEntity(NewMedicalHistoryRecordDto dto) {
        MedicalHistory medicalHistory = medicalHistoryService.findMedicalHistoryByID(dto.medicalHistoryID());
        return MedicalHistoryRecord.builder()
                .medicalHistory(medicalHistory)
                .identifiedIssues(dto.identifiedIssues())
                .treatment(dto.treatment())
                .build();
    }


    public MedicalHistoryRecordDto mapEntityToDto(MedicalHistoryRecord entity) {
        return MedicalHistoryRecordDto.builder()
                .id(entity.getId())
                .identifiedIssues(entity.getIdentifiedIssues())
                .treatment(entity.getTreatment())
                .creationDate(entity.getCreationDate())
                .build();
    }


    public NewMedicalHistoryRecordDto mapFileRepresentationToNewDtoWithPatientId(MedicalHistoryRecordFileRepresentation recordFile, Integer id) {
        MedicalHistory medicalHistory = medicalHistoryService.findByPatientId(id);
        return NewMedicalHistoryRecordDto.builder()
                .medicalHistoryID(medicalHistory.getId())
                .identifiedIssues(recordFile.getIdentifiedIssues())
                .treatment(recordFile.treatment)
                .build();

    }
}
