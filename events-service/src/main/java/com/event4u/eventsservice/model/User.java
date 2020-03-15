package com.event4u.eventsservice.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private List<Event> events;

    public User() {}

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
