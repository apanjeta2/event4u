package com.event4u.eventsservice.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String address;
    private Date date;
    private String description;
    private Boolean isActive;
    @ManyToOne //vise dogadjaja pripada istoj kategoriji
    private Category category;
    @ManyToOne
    private User creator;
    @ManyToOne
    private Location location;
    @OneToMany(mappedBy = "event") //Jedan dogadjas moze oznaciti vise korisnika
    private List<EventUser> eu;

    protected Event(String title, String address, Date date, String description, Boolean isActive, User creator) {
        this.title = title;
        this.address = address;
        this.date = date;
        this.description = description;
        this.isActive = isActive;
        this.creator = creator;
    }

    @Override
    public String toString() {
        return String.format(
                "Event[id=%d, title='%s', address='%s', description='%s']",
                id, title, address, description
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getActive() {
        return isActive;
    }

    public User getCreator() {
        return creator;
    }

    public Category getCategory() {
        return category;
    }
}
