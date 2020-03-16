package com.event4u.eventsservice.model;

import javax.persistence.*;

@Entity
@Table(name = "EventUser")
public class EventUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Event event;
    private Boolean isGoing;

    public EventUser(User user, Event event, Boolean isGoing) {
        this.user = user;
        this.event = event;
        this.isGoing = isGoing;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Event getEvent() {
        return event;
    }

    public Boolean getGoing() {
        return isGoing;
    }
}
