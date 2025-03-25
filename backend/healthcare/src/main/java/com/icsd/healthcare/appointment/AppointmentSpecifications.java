package com.icsd.healthcare.appointment;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentSpecifications {
    private AppointmentSpecifications() {
    }

    public static Specification<Appointment> hasPatientWithLastName(String patientsLastName) {
        return (root, query, cb) -> getPatientLastNamePredicate(root, cb, patientsLastName);
    }

    public static Specification<Appointment> hasPatientWithAmka(String patientsAmka) {
        return ((root, query, cb) -> getPatientsAmkaPredicate(root, cb, patientsAmka));
    }

    public static Specification<Appointment> hasStatus(Status status) {
        return (root, query, cb) -> getStatusPredicate(root, cb, status);
    }

    public static Specification<Appointment> hasScheduledDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) -> getDateRangePredicate(root, cb, startDate, endDate);
    }

    public static Specification<Appointment> withFilters(
            String patientsLastName, String patientsAmka, Status status, LocalDate startDate, LocalDate endDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (patientsLastName != null) {
                predicates.add(getPatientLastNamePredicate(root, cb, patientsLastName));
            }
            if (patientsAmka != null) {
                predicates.add(getPatientsAmkaPredicate(root, cb, patientsAmka));
            }
            if (endDate != null) {
                predicates.add(getEndDatePredicate(root, cb, endDate));
            }
            if (startDate != null) {
                predicates.add(getStartDatePredicate(root, cb, startDate));
            }
            if (status != null) {
                predicates.add(getStatusPredicate(root, cb, status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Appointment> withFilters(
            Integer patientsID, Status status, LocalDate startDate, LocalDate endDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (patientsID != null) {
                predicates.add(getPatientIdPredicate(root, cb, patientsID));
            }
            if (endDate != null) {
                predicates.add(getEndDatePredicate(root, cb, endDate));
            }
            if (startDate != null) {
                predicates.add(getStartDatePredicate(root, cb, startDate));
            }
            if (status != null) {
                predicates.add(getStatusPredicate(root, cb, status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Appointment> todaysActiveAppointments() {
        return (root, query, cb) -> {
            Predicate activeAppointmentsPredicate = getActiveAppointmentsPredicate(root);
            Predicate todaysAppointmentsPredicate = getTodaysAppointmentsPredicate(root, cb);
            return cb.and(activeAppointmentsPredicate, todaysAppointmentsPredicate);
        };
    }
    public static Specification<Appointment> todaysActiveAppointments(Integer patientID) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(patientID!=null){
                predicates.add(getPatientIdPredicate(root, cb, patientID));
            }
            predicates.add(getActiveAppointmentsPredicate(root));
            predicates.add(getTodaysAppointmentsPredicate(root, cb));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    //Predicates
    private static Predicate getPatientLastNamePredicate(Root<Appointment> root, CriteriaBuilder cb, String patientsLastName) {
        return cb.equal(root.get("patient").get("user").get("lastname"), patientsLastName);
    }

    private static Predicate getPatientsAmkaPredicate(Root<Appointment> root, CriteriaBuilder cb, String patientsAmka) {
        return cb.equal(root.get("patient").get("amka"), patientsAmka);
    }

    private static Predicate getStatusPredicate(Root<Appointment> root, CriteriaBuilder cb, Status status) {
        return cb.equal(root.get("status"), status);
    }

    private static Predicate getDateRangePredicate(Root<Appointment> root, CriteriaBuilder cb, LocalDate startDate, LocalDate endDate) {
        if (endDate != null) {
            endDate = endDate.plusDays(1).atStartOfDay().toLocalDate(); // Adjust endDate to include the full day
        }
        else{
            endDate = startDate.plusDays(1).atStartOfDay().toLocalDate();
        }
        return cb.between(root.get("date"), startDate, endDate);
    }

    private static Predicate getStartDatePredicate(Root<Appointment> root, CriteriaBuilder cb, LocalDate startDate) {
        return cb.greaterThanOrEqualTo(root.get("date"), startDate);
    }

    private static Predicate getEndDatePredicate(Root<Appointment> root, CriteriaBuilder cb, LocalDate endDate) {
        return cb.lessThanOrEqualTo(root.get("date"), endDate);
    }

    private static Predicate getActiveAppointmentsPredicate(Root<Appointment> root) {
        return root.get("status").in(Status.ATTENDED, Status.CREATED);
    }

    private static Predicate getTodaysAppointmentsPredicate(Root<Appointment> root, CriteriaBuilder cb) {
        LocalDate today = LocalDate.now();
        return cb.equal(root.get("date"), today);
    }

    private static Predicate getPatientIdPredicate(Root<Appointment> root, CriteriaBuilder cb, Integer patientId) {
        return cb.equal(root.get("patient").get("patientID"), patientId);
    }


}
