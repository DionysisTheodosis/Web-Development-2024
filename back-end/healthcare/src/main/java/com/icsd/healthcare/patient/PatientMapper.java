package com.icsd.healthcare.patient;

import com.icsd.healthcare.medicalhistory.MedicalHistory;
import com.icsd.healthcare.medicalhistory.MedicalHistoryNotFoundException;
import com.icsd.healthcare.medicalhistory.MedicalHistoryService;
import com.icsd.healthcare.user.User;
import com.icsd.healthcare.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PatientMapper {
    private final PasswordEncoder passwordEncoder;
    private final MedicalHistoryService medicalHistoryService;

    public Patient mapDtoToEntity(NewPatientDto dto) {

        User user = User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .personalID(dto.personalID())
                .userRole(UserRole.PATIENT)
                .build();

        return Patient.builder()
                .user(user)
                .amka(dto.amka())
                .build();
    }

    public PatientInfoDto mapEntityToPatientInfoDto(Patient entity) {
        MedicalHistory medicalHistory = entity.getMedicalHistory();
        Integer medicalHistoryId=null;

        if (medicalHistory != null) {
            medicalHistoryId=medicalHistory.getId();
        }
        else{
            try {
                medicalHistoryId = medicalHistoryService.getMedicalHistoryID(entity.getPatientID());
            }
            catch(MedicalHistoryNotFoundException ex){
                log.info("Medical History Not Found");
            }
        }
        return PatientInfoDto.builder()
                .patientID(entity.getPatientID())
                .firstName(entity.getUser().getFirstName())
                .lastName(entity.getUser().getLastName())
                .amka(entity.getAmka())
                .email(entity.getUser().getEmail())
                .personalID(entity.getUser().getPersonalID())
                .registrationDate(entity.getRegistrationDate())
                .medicalHistoryID(medicalHistoryId)
                .build();

    }

    public NewPatientDto mapPatientFileRepresentationToNewPatientDto(PatientFileRepresentation patient) {

        return NewPatientDto.builder()
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .amka(patient.getAmka())
                .email(patient.getEmail())
                .personalID(patient.getPersonalID())
                .password(patient.getPassword())
                .build();
    }

}