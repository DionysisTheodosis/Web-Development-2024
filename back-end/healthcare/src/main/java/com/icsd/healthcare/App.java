package com.icsd.healthcare;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Configurable
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
/*
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
			*//*UserMapper userMapper = new UserMapper();
			User user3 = userMapper.toUser(userSignUpDto);
			userRepository.save(user3);*//*



		};
	}*/
}