package com.event4u.eventsservice.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.time.LocalDate;

public class EventNotification implements Serializable {
    private Long eventId;
    private String  name;
    private LocalDate date;

    public EventNotification() {

    }

    @JsonCreator
    public EventNotification(Long eventId, String name, LocalDate date) {
        this.name = name;
        this.eventId=eventId;
        this.date=date;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }}
