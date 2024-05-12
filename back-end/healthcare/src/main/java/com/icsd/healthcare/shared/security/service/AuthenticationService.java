package com.icsd.healthcare.shared.security.service;


import com.icsd.healthcare.shared.exceptionhandler.ErrorCode;
import com.icsd.healthcare.shared.security.model.AuthenticationResponse;
import com.icsd.healthcare.user.User;
import com.icsd.healthcare.user.UserNotFoundException;
import com.icsd.healthcare.user.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(User request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .personalID(request.getPersonalID())
                .userRole(request.getUserRole())
                .build();

       userRepository.save(user);

        // Return an empty AuthenticationResponse or a message indicating successful registration
        return new AuthenticationResponse("Registration successful");
    }
    public AuthenticationResponse authenticate(User request){
        System.out.println("#########################mphka");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    ));
        }catch(AuthenticationException ex){

        }
          System.out.println("#########################vghka");
          User user = userRepository.findByEmail(request.getEmail()).orElseThrow(()-> new UserNotFoundException(ErrorCode.ERROR_USER_NOT_FOUND));
          System.out.println("aiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
          return new AuthenticationResponse (null);
    }

}
