package com.icsd.healthcare.secretary;

import com.icsd.healthcare.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class SecretaryService {
    private final UserService userService;

    public void createSecretary(SecretaryDto secretaryDto) {

        log.info("Creating secretary");

    }
}
