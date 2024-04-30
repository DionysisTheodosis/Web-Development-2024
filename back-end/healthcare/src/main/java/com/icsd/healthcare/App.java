package com.icsd.healthcare;

import com.icsd.healthcare.patient.model.Patient;
import com.icsd.healthcare.patient.repository.PatientRepository;
import com.icsd.healthcare.user.dto.UserSignUpDto;
import com.icsd.healthcare.user.mapper.UserMapper;
import com.icsd.healthcare.user.model.User;
import com.icsd.healthcare.user.model.UserRole;
import com.icsd.healthcare.user.repository.UserRepository;
import jakarta.validation.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public CommandLineRunner init(UserRepository userRepository, PatientRepository patientRepository) {
		return args -> {
			// Perform the search for a user by username
			String usernameToSearch = "dionysis";
			String amkaToSearch = "18069702878";
			User user = userRepository.findByFirstName(usernameToSearch);
			Patient patient = patientRepository.findByAmka(amkaToSearch);


			// Handle the result
			if (user != null) {
				System.out.println("user is "+ user);


			} else {
				System.out.println("User not found for username: " + usernameToSearch);

			}

			if(patient!= null){
				System.out.println("patient is "+ patient);
			}
			else{
				System.out.println("Patient not found for amka: " + amkaToSearch);
			}
			UserSignUpDto userSignUpDto = new UserSignUpDto(
					"dionysis",
					"theodosis",
					"theo_187@hotmail.com",
					"sdfs",
					"AB123456789",
					UserRole.DOCTOR
			);
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<UserSignUpDto>> violations = validator.validate(userSignUpDto);
			for (ConstraintViolation<UserSignUpDto> violation : violations) {
				System.out.println("Validation error: " + violation.getMessage());
			}
			System.out.println(userSignUpDto);
			/*UserMapper userMapper = new UserMapper();
			User user3 = userMapper.toUser(userSignUpDto);
			userRepository.save(user3);*/



		};
	}
}