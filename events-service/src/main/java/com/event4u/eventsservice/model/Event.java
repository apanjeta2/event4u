package com.event4u.eventsservice.model;

import com.event4u.eventsservice.EventsServiceApplication;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Event title cannot be null or empty")
    @NotBlank(message = "Event title cannot be null or empty")
    @Column(nullable=false)
    private String title;
    @NotNull(message = "Event address cannot be null or empty")
    @NotBlank(message = "Event address cannot be null or empty")
    @Column(nullable=false)
    private String address;
   // @Past(message = "Event with date in the past cannot be created")
    @NotNull(message = "Event date cannot be null or empty")
    @Column(nullable=false)
    private LocalDate date;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private Boolean isActive;
    //@NotNull(message = "Event must belong to a category")
    @ManyToOne //vise dogadjaja pripada istoj kategoriji
    private Category category;
    @NotNull(message = "Event must have a creator")
    @ManyToOne
    private User creator;
    @NotNull(message = "Event must have a location")
    @ManyToOne
    private Location location;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private List<EventUser> eu;
    private EventsServiceApplication.Status crateEventStatus;
    private Long beginTime;
    private Long endTime;

    protected Event() {}

    public Event(String title, String address, LocalDate date, String description, Boolean aTrue) {
        this.title = title;
        this.address = address;
        this.date = date;
        this.description = description;
        this.isActive = aTrue;
        this.crateEventStatus = EventsServiceApplication.Status.CREATE_STARTED;
    }

    public Event(String title, String address, LocalDate date, String description, Boolean isActive, Category category, User creator, Location location) {
        this.title = title;
        this.address = address;
        this.date = date;
        this.description = description;
        this.isActive = isActive;
        this.category = category;
        this.creator = creator;
        this.location = location;
        this.crateEventStatus = EventsServiceApplication.Status.CREATE_STARTED;
    }

    public Event(Long id, String title, String address, LocalDate date, String description, Category category, User creator, Location location,  Boolean isActive) {
        this.title = title;
        this.address = address;
        this.date = date;
        this.description = description;
        this.isActive = isActive;
        this.category = category;
        this.creator = creator;
        this.location = location;
        this.id = id;
    }

    public Event(Long id, String title, String address, LocalDate date, String description, Category category, User creator, Location location,  Boolean isActive, Long beginTime, Long endTime) {
        this.title = title;
        this.address = address;
        this.date = date;
        this.description = description;
        this.isActive = isActive;
        this.category = category;
        this.creator = creator;
        this.location = location;
        this.id = id;
        this.beginTime = beginTime;
        this.endTime = endTime;
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

    public LocalDate getDate() {
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

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public EventsServiceApplication.Status getCrateEventStatus() {
        return crateEventStatus;
    }

    public void setCrateEventStatus(EventsServiceApplication.Status crateEventStatus) {
        this.crateEventStatus = crateEventStatus;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
