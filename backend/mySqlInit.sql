create table t_slot
(
    slotID       int auto_increment
        primary key,
    slotDateTime datetime         not null,
    duration     int default 1800 not null
)
    engine = InnoDB
    collate = utf8mb4_unicode_ci;

create table t_user
(
    userID     int auto_increment
        primary key,
    firstName  varchar(50)                             not null,
    lastName   varchar(50)                             not null,
    email      varchar(100)                            not null,
    pass       varchar(100)                            not null,
    personalID char(8)                                 not null,
    userRole   enum ('PATIENT', 'SECRETARY', 'DOCTOR') not null,
    constraint email_UNIQUE
        unique (email),
    constraint personalID_UNIQUE
        unique (personalID)
)
    engine = InnoDB
    collate = utf8mb4_unicode_ci;

create table t_doctor
(
    doctorID  int auto_increment
        primary key,
    specialty varchar(100) not null,
    userID    int          null,
    constraint userID
        unique (userID),
    constraint t_doctor_ibfk_1
        foreign key (userID) references t_user (userID)
            on update cascade on delete cascade
)
    engine = InnoDB
    collate = utf8mb4_unicode_ci;

create table t_doctorAvailability
(
    slotID   int not null,
    doctorID int not null,
    primary key (slotID, doctorID),
    constraint slotID_UNIQUE
        unique (slotID),
    constraint t_doctorAvailability_ibfk_1
        foreign key (doctorID) references t_doctor (doctorID)
            on update cascade on delete cascade,
    constraint t_doctorAvailability_ibfk_2
        foreign key (slotID) references t_slot (slotID)
            on update cascade on delete cascade
)
    engine = InnoDB
    collate = utf8mb4_unicode_ci;

create index doctorID
    on t_doctorAvailability (doctorID);

create table t_patient
(
    patientID        int auto_increment
        primary key,
    amka             char(11)                            not null,
    registrationDate timestamp default CURRENT_TIMESTAMP null,
    userID           int                                 null,
    constraint amka_UNIQUE
        unique (amka),
    constraint userID
        unique (userID),
    constraint t_patient_ibfk_1
        foreign key (userID) references t_user (userID)
            on update cascade on delete cascade
)
    engine = InnoDB
    collate = utf8mb4_unicode_ci;

create table t_appointment
(
    appID           int auto_increment
        primary key,
    appDate         date                                not null,
    appTime         time                                not null,
    appReason       varchar(255)                        not null,
    appCreationDate timestamp default CURRENT_TIMESTAMP null,
    appStatus       tinyint                             null,
    appPatientID    int                                 null,
    appDoctorID     int                                 null,
    constraint t_appointment_ibfk_1
        foreign key (appPatientID) references t_patient (patientID)
            on update cascade on delete cascade,
    constraint t_appointment_ibfk_2
        foreign key (appDoctorID) references t_doctor (doctorID)
            on update cascade on delete cascade
)
    engine = InnoDB
    collate = utf8mb4_unicode_ci;

create index appDoctorID
    on t_appointment (appDoctorID);

create index appPatientID
    on t_appointment (appPatientID);

create table t_medicalHistory
(
    historyID int auto_increment
        primary key,
    patientID int not null,
    constraint patientID_UNIQUE
        unique (patientID),
    constraint t_medicalHistory_ibfk_1
        foreign key (patientID) references t_patient (patientID)
            on update cascade on delete cascade
)
    engine = InnoDB
    collate = utf8mb4_unicode_ci;

create table t_medicalHistoryRecord
(
    recordID         int auto_increment
        primary key,
    historyID        int                                       not null,
    creationDate     timestamp default CURRENT_TIMESTAMP null,
    identifiedIssues varchar(255)                              not null,
    treatment        varchar(255)                              not null,
    constraint t_medicalHistoryRecord_ibfk_1
        foreign key (historyID) references t_medicalHistory (historyID)
            on update cascade on delete cascade
)
    engine = InnoDB
    collate = utf8mb4_unicode_ci;

create index historyID
    on t_medicalHistoryRecord (historyID);

