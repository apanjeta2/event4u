package com.event4u.notificationservice.exceptionHandler;

public class NotificationNotFoundException extends RuntimeException {

    public NotificationNotFoundException(Long id) {
        super("Notification id not found : " + id);
    }

}
