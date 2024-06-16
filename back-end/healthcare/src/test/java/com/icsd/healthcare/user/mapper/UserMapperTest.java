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
                "MyName",
                "MyLastName",
                "something@hotmail.com",
                "secretPass",
                "AZ111121",
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
                "MyName",
                "MyLastName",
                "something@hotmail.com",
                "sdfs",
                "AB123456789",
                UserRole.DOCTOR
        );

        Set<ConstraintViolation<UserSignUpDto>> violations = validator.validate(userSignUpDto);


        for (ConstraintViolation<UserSignUpDto> violation : violations) {
            String message = violation.getMessage();
            assertTrue(message.contains("Personal ID length should be..."));
        }
    }

}