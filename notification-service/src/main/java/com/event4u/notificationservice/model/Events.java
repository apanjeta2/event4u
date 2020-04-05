package com.event4u.notificationservice.model;

import javax.validation.constraints.*;
import javax.persistence.*;

import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Events {

    @Id
    @NotNull(message = "Event id cannot be null")
    private Long eventId;

    @OneToMany(mappedBy = "event")
    private List<Notification> notifications;

    @ManyToMany(mappedBy = "events")
    private Set<User> users = new HashSet<>();

    protected Events() {
    }
    public Events(Long id) {
        this.eventId=id;
    }

    @Override
    public String toString() {
        return String.format(
                "Event[id=%d]",
                eventId);
    }

    public Long getEventId() {
        return eventId;
    }
    public void setEventId(Long eventId) {
        this.eventId=eventId;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
