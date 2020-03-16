package com.event4u.eventsservice.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
     @OneToMany(mappedBy = "category") //Jedna kategorija ima više događaja
     private List<Event> events;

    protected Category() {}

    public Category(String name, List<Event> events) {
        this.name = name;
        this.events = events;
    }

    @Override
    public String toString() {
        return String.format(
                "Category[id=%d, name='%s']",
                id, name
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Event> getEvents() {
        return events;
    }
}
