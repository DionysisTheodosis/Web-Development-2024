package com.icsd.healthcare;

import com.icsd.healthcare.doctor.Doctor;
import com.icsd.healthcare.doctor.DoctorRepository;
import com.icsd.healthcare.patient.PatientRepository;
import com.icsd.healthcare.patient.PatientService;
import com.icsd.healthcare.user.User;
import com.icsd.healthcare.user.UserRepository;
import com.icsd.healthcare.user.UserRole;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configurable
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	/*@Bean
	public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder, DoctorRepository doctorRepository) {
		return args ->
			doctorRepository.save(
					Doctor.builder()
							.specialty("Ορθοπεδικός")
							.user(
									User.builder()
											.firstName("Nikos")
											.lastName("Papadopoulos")
											.email("nick@hotmail.com")
											.password(passwordEncoder.encode("123"))
											.userRole(UserRole.DOCTOR)
											.personalID("AT123456")
											.build()
							).build()
			);

	}*/
}