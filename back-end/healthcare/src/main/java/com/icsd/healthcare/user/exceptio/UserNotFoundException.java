package com.icsd.healthcare.user.exceptio;

public class UserNotFoundException  extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}

