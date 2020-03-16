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

    protected EventUser() {};

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

    @Override
    public String toString() {
        return String.format(
                "User with id=%d is going to event with id=%d",
                id, user.getId(), event.getId()
        );
    }
}
