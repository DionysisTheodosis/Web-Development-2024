package com.icsd.healthcare.medicalhistory;

import com.icsd.healthcare.medicalhistoryrecord.MedicalHistoryRecord;
import com.icsd.healthcare.patient.Patient;
import com.icsd.healthcare.patient.PatientNotFoundException;
import com.icsd.healthcare.patient.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;



@RequiredArgsConstructor
@Service
public class MedicalHistoryService {
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final PatientRepository patientRepository;

    public Integer getMedicalHistoryID(){

       return null;
    }

    public Integer getMedicalHistoryID(int patientID){
        if(!patientRepository.existsById(patientID)){
            throw new PatientNotFoundException();
        }

        return medicalHistoryRepository.findByPatient_PatientID(patientID).orElseThrow(MedicalHistoryNotFoundException::new).getId();
    }

    public MedicalHistory createMedicalHistory(Patient patient){
        if(!patientRepository.existsById(patient.getPatientID())){
            throw new PatientNotFoundException();
        }
       return medicalHistoryRepository.save(
                MedicalHistory.builder()
                        .patient(patient)
                        .build()
        );
    }
    public MedicalHistory findMedicalHistoryByID(int medicalHistoryID){
        return medicalHistoryRepository.findById(medicalHistoryID).orElseThrow(MedicalHistoryNotFoundException::new);
    }

    @Transactional
    public  MedicalHistory saveMedicalHistory(MedicalHistory medicalHistory){

        try{
            medicalHistory = medicalHistoryRepository.save(medicalHistory);
        }
        catch(Exception e){
            throw new MedicalHistorySaveException();
        }

       return medicalHistory;
    }

    public Set<MedicalHistoryRecord> getMedicalHistoryRecords(int historyId) {
        MedicalHistory medicalHistory = getMedicalHistoryById(historyId);
        return medicalHistory.getMedicalHistoryRecords();
    }

    public MedicalHistory getMedicalHistoryById(int historyId) {
        return medicalHistoryRepository.findById(historyId)
                .orElseThrow(MedicalHistoryNotFoundException::new);
    }

    public MedicalHistory findByPatientId(Integer patientID){
        return medicalHistoryRepository.findByPatient_PatientID(patientID).orElseThrow(MedicalHistoryNotFoundException::new);
    }

}
