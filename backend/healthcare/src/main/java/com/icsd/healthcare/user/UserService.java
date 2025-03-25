package com.icsd.healthcare.user;

import com.icsd.healthcare.shared.exception.InvalidOperationException;
import com.icsd.healthcare.shared.utils.AuthUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
private final AuthUtils authUtil;
    private final UserMapper userMapper;

    public Optional<User> saveUser(User user) {
        return Optional.of(userRepository.save(user));
    }

    public Boolean isUserExistsByPersonalIDAndEmail(String personalID, String email) {
        return userRepository.existsByPersonalIDOrEmail(personalID,email);
    }

    public boolean isUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User getAuthenticatedUser(){
        String email = authUtil.getAuthenticatedUserEmail();
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
    public boolean isSignInUserDoctor(){
        return getAuthenticatedUser().isUserDoctor();
    }
    public boolean isSignInUserPatient(){
        return getAuthenticatedUser().isUserPatient();
    }
    public boolean isSignInUserSecretary(){
        return getAuthenticatedUser().isUserSecretary();
    }

    @Transactional
    public void changePassword(ChangePasswordDto changePasswordDto) {
        User user = getAuthenticatedUser();
        if(!user.getPassword().equals(passwordEncoder.encode(changePasswordDto.oldPassword()))) {
            throw new InvalidOperationException("Old password does not match");
        }
        if(user.getPassword().equals(passwordEncoder.encode(changePasswordDto.newPassword()))){
            throw new InvalidOperationException("New password cannot be the same as the old password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDto.newPassword()));
        userRepository.save(user);
    }

    public UserDetailsDto getOwnUserDetails() {
        User user = getAuthenticatedUser();
        return userMapper.userEntityToUserDetailsDto(user);
    }

    @Transactional
    public UserDetailsDto changeOwnUserDetails(@NotNull UserDetailsDto userDetailsDto) {
        User user = getAuthenticatedUser();

        if(!userDetailsDto.firstName().equals(user.getFirstName())) {
            user.setFirstName(userDetailsDto.firstName());
        }
        if(!userDetailsDto.lastName().equals(user.getLastName())) {
            user.setLastName(userDetailsDto.lastName());
        }
        if(!userDetailsDto.email().equals(user.getEmail())) {
            user.setEmail(userDetailsDto.email());
        }
        if(!userDetailsDto.personalID().equals(user.getPersonalID())) {
            user.setPersonalID(userDetailsDto.personalID());
        }
        return userMapper.userEntityToUserDetailsDto(saveUser(user).orElseThrow(UserNotFoundException::new));
    }

    public User getUserByID(Integer userId){
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public Set<User> getUsers(UserRole userRole){
        return userRepository.findAllByUserRole(userRole).orElseThrow(UserNotFoundException::new);
    }
}
