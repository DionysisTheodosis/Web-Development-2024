CREATE TABLE t_role (
	role_name VARCHAR(255) PRIMARY KEY
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE t_user (
	identity_number CHAR(8) PRIMARY KEY ,
	email VARCHAR(255) NOT NULL UNIQUE,
	pass VARCHAR(255) NOT NULL,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	user_role VARCHAR(255) NOT NULL,
	FOREIGN KEY (user_role) REFERENCES t_role(role_name) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE t_patient (
	amka CHAR(11) PRIMARY KEY,
	user_id CHAR(8) NOT NULL UNIQUE,
	registration_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (user_id) REFERENCES t_user( identity_number) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE t_doctor (
	doctor_id INT PRIMARY KEY AUTO_INCREMENT,
	user_id CHAR(8) NOT NULL UNIQUE,
	specialty VARCHAR(255) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES t_user(identity_number) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE t_status (
  status_name VARCHAR(255) PRIMARY KEY
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE t_appointment (
	appointment_id INT PRIMARY KEY AUTO_INCREMENT,
	patient_id CHAR(11) NOT NULL,
	doctor_id INT NOT NULL,
	appointment_date DATE NOT NULL,
	appointment_time TIME NOT NULL,
	reason_for_visit VARCHAR(255) NOT NULL,
	creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	status_name VARCHAR(255) NOT NULL DEFAULT 'Created',
	FOREIGN KEY (patient_id) REFERENCES t_patient(amka) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (doctor_id) REFERENCES t_doctor(doctor_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (status_name) REFERENCES t_status(status_name) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


CREATE TABLE t_medical_history (
	history_id INT PRIMARY KEY AUTO_INCREMENT,
	patient_id  CHAR(11) NOT NULL unique,
	FOREIGN KEY (patient_id) REFERENCES t_patient(amka) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE t_medical_history_record (
  record_id INT PRIMARY KEY AUTO_INCREMENT,
  history_id INT NOT NULL,
  creation_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  identified_issues VARCHAR(255) NOT NULL,
  treatment VARCHAR(255) NOT NULL,
  FOREIGN KEY (history_id) REFERENCES t_medical_history(history_id) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE t_doctor_availability (
  availability_id INT PRIMARY KEY AUTO_INCREMENT,
  doctor_id INT NOT NULL,
  slot DATETIME NOT NULL,
  FOREIGN KEY (doctor_id) REFERENCES t_doctor(doctor_id) ON DELETE CASCADE ON UPDATE CASCADE
)CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;


INSERT INTO t_role (role_name) VALUES
  ('Patient'),
  ('Doctor'),
  ('Front Desk Staff');

INSERT INTO t_user (identity_number, email, pass, firstname, lastname, user_role) VALUES
  ('12345678', 'john.smith@example.com', 'password1', 'Smith', 'John', 'Patient'),
  ('87654321', 'jane.doe@example.com', 'password2', 'Doe', 'Jane', 'Patient'),
  ('90123456', 'michael.lee@example.com', 'password3', 'Lee', 'Michael', 'Doctor'),
  ('23456789', 'sophia.hernandez@example.com', 'password4', 'Hernandez', 'Sophia', 'Doctor'),
  ('56789012', 'david.white@example.com', 'password5', 'White', 'David', 'Front Desk Staff');


INSERT INTO t_patient (amka, user_id, registration_date) VALUES
  ('11111111111', '12345678', NOW()),
  ('22222222222', '87654321', NOW());

INSERT INTO t_status (status_name) VALUES
  ('Created'),
  ('Pending'),
  ('Approved'),
  ('Completed'),
  ('Canceled');
  
  INSERT INTO t_doctor (user_id, specialty) VALUES
  ('90123456', 'Cardiology'),
  ('23456789', 'General Practice');


INSERT INTO t_appointment (patient_id, doctor_id, appointment_date, appointment_time, reason_for_visit, creation_date, status_name) VALUES
  ('11111111111', 1, '2024-04-30', '10:00:00', 'Checkup', NOW(), 'Created'),
  ('22222222222', 2, '2024-05-02', '14:00:00', 'Flu symptoms', NOW(), 'Created');

INSERT INTO t_medical_history (patient_id) VALUES
  ('11111111111'),
  ('22222222222');
  
  INSERT INTO t_medical_history_record (history_id, creation_date, identified_issues, treatment) VALUES
  (1, '2024-04-20 15:00:00', 'High blood pressure', 'Medication prescribed'),
  (2, '2024-04-22 09:00:00', 'Minor cold', 'Over-the-counter medication recommended');


INSERT INTO t_doctor_availability (doctor_id, slot) VALUES
  (1, '2024-04-30 09:00:00'),
  (1, '2024-04-30 11:00:00'),
  (2, '2024-05-02 13:00:00');


