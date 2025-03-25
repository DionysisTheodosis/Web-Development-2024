package com.icsd.healthcare.medicalhistoryrecord;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MedicalHistoryRecordDto(

        Integer id,

        LocalDateTime creationDate,

        String identifiedIssues,

        String treatment,

        Integer appointmentId

) {
}
