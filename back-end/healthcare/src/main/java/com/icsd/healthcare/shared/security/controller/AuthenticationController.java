package com.icsd.healthcare.shared.security.controller;

import com.icsd.healthcare.shared.security.model.AuthenticationResponse;
import com.icsd.healthcare.shared.security.service.AuthenticationService;
import com.icsd.healthcare.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {

/*    @GetMapping("/login")
    public ResponseEntity<HttpStatus> searchAppointments(HttpSession session) {
        session.setAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, "nikgiak@hotmail.com");
        System.out.println(session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME));
        return ResponseEntity.status(100).body(HttpStatus.OK);
    }*/

    @GetMapping("/as")
    public ResponseEntity<String> searchAppointmen(HttpServletRequest request) {
        System.out.println();
        return ResponseEntity.status(HttpStatus.OK).body(Optional.of(request.getUserPrincipal().getName()).orElseThrow(()->new RuntimeException("User not found")));
    }
    private final AuthenticationService authenticationService;
/*    @CrossOrigin
    @GetMapping("/as")
    public String register(HttpServletRequest request) {
        request.getSession().setAttribute("user");
        return "hi";
    }*/
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request) {
        System.out.println("######regi");
        return ResponseEntity.ok(authenticationService.register(request));
    }

/*    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }*/

}
