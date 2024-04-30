package com.icsd.healthcare.user.mapper;

import com.icsd.healthcare.user.dto.UserSignUpDto;
import com.icsd.healthcare.user.model.User;
import com.icsd.healthcare.user.model.UserRole;
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
    void  shouldMapUserThrowPersonalIDNotAcceptableLenght(){
        UserSignUpDto userSignUpDto = new UserSignUpDto(
                "dionysis",
                "theodosis",
                "theo_187@hotmail.com",
                "sdfs",
                "AB123456789",
                UserRole.DOCTOR
        );

        Set<ConstraintViolation<UserSignUpDto>> violations = validator.validate(userSignUpDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserSignUpDto> violation : violations) {
                assertThrows(RuntimeException.class, () -> {
                    try {
                        throw new RuntimeException("Validation error: " + violation.getMessage());
                    }
                    catch(RuntimeException ex){
                            System.out.println(ex.getMessage());
                        }
                });

            }
        }
    }
}