package com.icsd.healthcare.secretary;

import com.icsd.healthcare.user.User;
import com.icsd.healthcare.user.UserRole;
import org.springframework.stereotype.Component;

@Component
public class SecretaryMapper  {

    public User mapDtoToEntity(SecretaryDto dto) {
        return User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .personalID(dto.personalID())
                .password(dto.password())
                .userRole(UserRole.SECRETARY)
                .build();

    }


    public SecretaryInfoDto mapEntityToDto(User entity) {
        return SecretaryInfoDto.builder()
                .id(entity.getId())
                .lastName(entity.getLastName())
                .firstName(entity.getFirstName())
                .email(entity.getEmail())
                .personalID(entity.getPersonalID())
                .build();
    }
}
