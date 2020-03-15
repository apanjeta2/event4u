package com.event4u.notificationservice;

import javax.persistence.*;

import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Events {

    @Id
    private Long eventId;

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

    @ManyToMany(mappedBy = "eventsList")
    private List<User> userList;
}
