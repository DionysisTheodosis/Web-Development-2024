package com.icsd.healthcare.medicalhistoryrecord;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record NewMedicalHistoryRecordDto(

        Integer medicalHistoryID,

        LocalDateTime creationDate,

        String identifiedIssues,

        String treatment

) {
}
