package com.icsd.healthcare.medicalhistoryrecord;

import com.opencsv.bean.CsvBindByName;
import com.poiji.annotation.ExcelCellName;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MedicalHistoryRecordFileRepresentation {

    @ExcelCellName("Medical History Id")
    @NotBlank
    @CsvBindByName(column = "Medical History Id")
    Integer medicalHistoryId;

    @ExcelCellName("Treatment")
    @NotBlank
    @CsvBindByName(column = "Treatment")
    String treatment;

    @ExcelCellName("Identified Issues")
    @NotBlank
    @CsvBindByName(column = "Identified Issues")
    String identifiedIssues;

}
