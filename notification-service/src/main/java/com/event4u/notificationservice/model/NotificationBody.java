package com.event4u.notificationservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.time.LocalDate;

public class NotificationBody implements Serializable {

    private Long eventId;
    private String  name;
    private LocalDate date;
    private String token;


    @JsonCreator
    public NotificationBody(Long eventId, String name, LocalDate date) {
        this.name = name;
        this.eventId=eventId;
        this.date=date;
    }

    @JsonCreator
    public NotificationBody(Long eventId, String name, LocalDate date, String token) {
        this.name = name;
        this.eventId=eventId;
        this.date=date;
        this.token=token;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getToken() {return token;}


}
