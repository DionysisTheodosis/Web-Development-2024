package com.icsd.healthcare.patient;


import com.icsd.healthcare.shared.validators.ValidAmka;
import com.icsd.healthcare.shared.validators.ValidName;
import com.icsd.healthcare.shared.validators.ValidPersonalID;
import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record PatientInfoDto(
        Integer patientID,

        @ValidName
        String firstName,

        @ValidName
        String lastName,

        @Email
        String email,

        @ValidAmka
        String amka,

        @ValidPersonalID
        String personalID,

        Integer medicalHistoryID,
        LocalDateTime registrationDate

)  {
}