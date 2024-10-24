package com.icsd.healthcare.patient;

import com.icsd.healthcare.shared.validators.ValidAmka;
import com.icsd.healthcare.shared.validators.ValidName;
import com.icsd.healthcare.shared.validators.ValidPersonalID;
import com.opencsv.bean.CsvBindByName;
import com.poiji.annotation.ExcelCellName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PatientFileRepresentation {

    @ExcelCellName("FirstName")
    @ValidName
    @CsvBindByName(column = "FirstName")
    String firstName;

    @ExcelCellName("LastName")
    @ValidName
    @CsvBindByName(column = "LastName")
    String lastName;

    @ExcelCellName("Email")
    @Email
    @CsvBindByName(column = "Email")
    String email;

    @ExcelCellName("Password")
    @NotBlank
    @CsvBindByName(column = "Password")
    String password;

    @ExcelCellName("PersonalID")
    @ValidPersonalID
    @CsvBindByName(column = "PersonalID")
    String personalID;

    @ExcelCellName("AMKA")
    @ValidAmka
    @CsvBindByName(column = "AMKA")
    String amka;

}
