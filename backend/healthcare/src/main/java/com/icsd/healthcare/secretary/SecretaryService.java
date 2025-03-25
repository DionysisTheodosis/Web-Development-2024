package com.icsd.healthcare.secretary;

import com.icsd.healthcare.shared.exception.InvalidOperationException;
import com.icsd.healthcare.user.User;
import com.icsd.healthcare.user.UserRole;
import com.icsd.healthcare.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class SecretaryService {
    private final UserService userService;
    private final SecretaryMapper secretaryMapper;

    public SecretaryInfoDto createSecretary(SecretaryDto secretaryDto) {
        User user =  userService.saveUser(secretaryMapper.mapDtoToEntity(secretaryDto)).orElseThrow(()-> new InvalidOperationException("Secretary didnt created"));
        return secretaryMapper.mapEntityToDto(user);
    }

    public SecretaryInfoDto getSecretary(Integer secretaryId) {
        User user = userService.getUserByID(secretaryId);
        return secretaryMapper.mapEntityToDto(user);
    }

    public Set<SecretaryInfoDto> getSecretaries() {
        Set<User> users = userService.getUsers(UserRole.SECRETARY);
        return  users.stream().map(secretaryMapper::mapEntityToDto).collect(Collectors.toSet());
    }


}
