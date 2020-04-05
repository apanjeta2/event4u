package com.event4u.eventsservice.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    private Long id;
    //Jedan korisnik moze kreirati vise dogadjaja
    @OneToMany(mappedBy = "creator")
    private List<Event> events;
    @OneToMany(mappedBy = "user") //Jedan korisnik moze oznaciti vise dogadjaja
    private List<EventUser> eu;

    protected User() {

    }

    public User(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d]",
                id
        );
    }

    public Long getId() {
        return id;
    }
}
