package com.icsd.healthcare.user.mapper;


import com.icsd.healthcare.user.dto.UserSignUpDto;
import com.icsd.healthcare.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(final @Valid UserSignUpDto userDto) {
        User user = new User();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        user.setUserRole(userDto.userRole());
        user.setPersonalID(userDto.personalID());
        return user;
    }

    public UserSignUpDto toUserDto(final User user) {
        return new UserSignUpDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPersonalID(),
                user.getUserRole()
        );


    }
}
