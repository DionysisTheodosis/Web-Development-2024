package com.icsd.healthcare.user.mapper;

import com.icsd.healthcare.user.UserMapper;
import com.icsd.healthcare.user.UserSignUpDto;
import com.icsd.healthcare.user.User;
import com.icsd.healthcare.user.UserRole;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserMapperTest {
    @Autowired
    Validator validator;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void shouldMapUserDtoToUser() {
        UserSignUpDto userSignUpDto = new UserSignUpDto(
                "Dionysis",
                "theodosis",
                "theo_187@hotmailcom",
                "secretPass",
                "11112",
                UserRole.DOCTOR
        );

        assertNotNull(userSignUpDto.userRole());

        User user =userMapper.toUser(userSignUpDto);
        assertEquals(userSignUpDto.firstName(),user.getFirstName());
        assertEquals(userSignUpDto.lastName(),user.getLastName());
        assertEquals(userSignUpDto.email(),user.getEmail());
        assertEquals(userSignUpDto.password(),user.getPassword());
        assertEquals(userSignUpDto.personalID(),user.getPersonalID());
        assertEquals(userSignUpDto.userRole(),user.getUserRole());
    }

    @Test
    void shouldMapUserThrowPersonalIDNotAcceptableLenght() {
        UserSignUpDto userSignUpDto = new UserSignUpDto(
                "dionysis",
                "theodosis",
                "theo_187@hotmail.com",
                "sdfs", // Short personal ID
                "AB123456789",
                UserRole.DOCTOR
        );

        Set<ConstraintViolation<UserSignUpDto>> violations = validator.validate(userSignUpDto);
       // assertTrue(violations.isEmpty(), "Expected validation errors");

        for (ConstraintViolation<UserSignUpDto> violation : violations) {
            String message = violation.getMessage();
            // Assert that the message contains the expected error for personal ID length
            assertTrue(message.contains("Personal ID length should be..."));
        }
    }

    /*todo: 1 make new repo*/
}