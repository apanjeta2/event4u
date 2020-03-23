package com.event4u.notificationservice.exceptionHandler;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(Long id) {
        super("Event id not found : " + id);
    }

}