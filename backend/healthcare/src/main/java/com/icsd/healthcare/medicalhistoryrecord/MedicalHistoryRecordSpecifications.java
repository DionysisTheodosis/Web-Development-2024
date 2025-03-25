package com.icsd.healthcare.medicalhistoryrecord;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MedicalHistoryRecordSpecifications {

    private MedicalHistoryRecordSpecifications() {}

    public static  Specification<MedicalHistoryRecord> hasPatientId(Integer patientId) {
        return (root, query, cb) -> cb.equal(root.get("medicalHistory").get("patient").get("patientID"), patientId);
    }

    public static Specification<MedicalHistoryRecord> createdBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) -> cb.between(root.get("creationDate"), startDate, endDate);
    }

    public static Specification<MedicalHistoryRecord> hasIdentifiedIssues(String identifiedIssues) {
        return (root, query, cb) -> cb.equal(root.get("identifiedIssues"), identifiedIssues);
    }

    public  static Specification<MedicalHistoryRecord> hasTreatment(String treatment) {
        return (root, query, cb) -> cb.equal(root.get("treatment"), treatment);
    }

    public static Specification<MedicalHistoryRecord> withFilters(
            Integer patientId, LocalDate startDate, LocalDate endDate, String identifiedIssues
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (patientId != null) {
                predicates.add(cb.equal(root.get("medicalHistory").get("patient").get("patientID"), patientId));
            }
            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("creationDate"), endDate));
            }
            if (startDate!=null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("creationDate"), startDate));
            }
            if (identifiedIssues != null) {
                predicates.add(cb.equal(root.get("identifiedIssues"), identifiedIssues));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}