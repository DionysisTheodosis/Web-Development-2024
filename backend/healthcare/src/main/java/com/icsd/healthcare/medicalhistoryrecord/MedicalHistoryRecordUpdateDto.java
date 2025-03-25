package com.icsd.healthcare.medicalhistoryrecord;

import lombok.Builder;

@Builder
public record MedicalHistoryRecordUpdateDto(
        String identifiedIssues,

        String treatment
) {
}
