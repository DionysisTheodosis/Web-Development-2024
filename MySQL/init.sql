-- Πίνακας Χρήστης
CREATE TABLE t_user (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    pass VARCHAR(100) NOT NULL,
    personalID CHAR(8) NOT NULL,
    userRole ENUM('PATIENT', 'SECRETARY', 'DOCTOR') NOT NULL
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Πίνακας Ασθενής
CREATE TABLE t_patient (
    patientID INT PRIMARY KEY AUTO_INCREMENT,
    amka CHAR(11) NOT NULL,
    registrationDate DATE NOT NULL,
    userID INT UNIQUE,
    FOREIGN KEY (userID) REFERENCES t_user(userID) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Πίνακας Ιατρός
CREATE TABLE t_doctor (
    doctorID INT PRIMARY KEY AUTO_INCREMENT,
    specialty VARCHAR(100) NOT NULL,
    userID INT UNIQUE,
    FOREIGN KEY (userID) REFERENCES t_user(userID) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Πίνακας Ραντεβού
CREATE TABLE t_appointment (
    appID INT PRIMARY KEY AUTO_INCREMENT,
    appDate DATE NOT NULL,
    appTime TIME NOT NULL,
    appReason VARCHAR(255) NOT NULL,
    appCreationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    appStatus ENUM('created', 'attended', 'completed', 'cancelled') NOT NULL,
    appPatientID INT,
    appDoctorID INT,
    FOREIGN KEY (appPatientID) REFERENCES t_patient(PatientID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (appDoctorID) REFERENCES t_doctor(doctorID) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE t_medicalHistory (
    historyID INT PRIMARY KEY AUTO_INCREMENT,
    patientID INT NOT NULL,
    FOREIGN KEY (patientID) REFERENCES t_patient(PatientID) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Πίνακας Εγγραφή Ιστορικού
CREATE TABLE t_medicalHistoryRecord (
    recordID INT PRIMARY KEY AUTO_INCREMENT,
    historyID INT NOT NULL,
    creationDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    identifiedIssues VARCHAR(255) NOT NULL,
    treatment VARCHAR(255) NOT NULL,
    FOREIGN KEY (historyID) REFERENCES t_medicalHistory(historyID) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- Πίνακας Διαθεσιμότητας Ιατρού
CREATE TABLE t_doctorAvailability (
    availabilityID INT PRIMARY KEY AUTO_INCREMENT,
    slot DATETIME NOT NULL,
    doctorID INT,
    FOREIGN KEY (doctorID) REFERENCES t_doctor(doctorID) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
