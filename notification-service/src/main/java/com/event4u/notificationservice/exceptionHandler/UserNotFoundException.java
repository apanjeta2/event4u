package com.event4u.notificationservice.exceptionHandler;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User id not found : " + id);
    }

}

