package com.icsd.healthcare.medicalhistoryrecord;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record NewMedicalHistoryRecordDto(

        @NotNull
        Integer medicalHistoryID,

        @NotBlank
        String identifiedIssues,

        @NotBlank
        String treatment,

        @NotNull
        Integer appointmentId

) {
}
