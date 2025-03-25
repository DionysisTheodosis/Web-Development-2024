package com.icsd.healthcare.medicalhistoryrecord;

import com.icsd.healthcare.appointment.AppointmentService;
import com.icsd.healthcare.medicalhistory.MedicalHistory;
import com.icsd.healthcare.medicalhistory.MedicalHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MedicalHistoryRecordMapper{
    private final MedicalHistoryService medicalHistoryService;
    private final AppointmentService appointmentService;


    public MedicalHistoryRecord mapDtoToEntity(NewMedicalHistoryRecordDto dto) {
        MedicalHistory medicalHistory = medicalHistoryService.findMedicalHistoryByID(dto.medicalHistoryID());
        return MedicalHistoryRecord.builder()
                .medicalHistory(medicalHistory)
                .identifiedIssues(dto.identifiedIssues())
                .treatment(dto.treatment())
                .appointment(appointmentService.getAppointment(dto.appointmentId()))
                .build();
    }


    public MedicalHistoryRecordDto mapEntityToDto(MedicalHistoryRecord entity) {

        if (null != entity.getAppointment()) {
            return MedicalHistoryRecordDto.builder()
                    .id(entity.getId())
                    .identifiedIssues(entity.getIdentifiedIssues())
                    .treatment(entity.getTreatment())
                    .appointmentId(entity.getAppointment().getId())
                    .build();
        }

        return MedicalHistoryRecordDto.builder()
                .id(entity.getId())
                .identifiedIssues(entity.getIdentifiedIssues())
                .treatment(entity.getTreatment())
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
