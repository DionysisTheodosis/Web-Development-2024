package com.icsd.healthcare.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> saveUser(User user) {

        return Optional.of(userRepository.save(user));
    }

    public Boolean isUserExistsByPersonalIDAndEmail(String personalID, String email) {
        return userRepository.existsByPersonalIDOrEmail(personalID,email);
    }

    public boolean isUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
