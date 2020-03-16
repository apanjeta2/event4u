package com.event4u.eventsservice.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "EventsUsers")
public class EventsUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean isGoing;
//    @OneToMany
//    private List<Event> events;
//    @OneToMany
//    private List<User> users;

    public EventsUsers(Boolean isGoing, List<Event> events, List<User> users) {
        this.isGoing = isGoing;
//        this.events = events;
//        this.users = users;
    }

    @Override
    public String toString() {
        return String.format(
                "EventsUsers[id=%d]",
                id
        );
    }

    public Long getId() {
        return id;
    }

//    public List<User> getUsers() {
//        return users;
//    }
//
//    public List<Event> getEvents() {
//        return events;
//    }`

    public Boolean getGoing() {
        return isGoing;
    }
}