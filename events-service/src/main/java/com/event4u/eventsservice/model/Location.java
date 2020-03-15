package com.event4u.eventsservice.model;


import javafx.util.Pair;

import javax.persistence.*;

@Entity
@Table(name = "Location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Tuple coordinates;
    private String city;
    private String country;

    protected Location(Tuple coordinates, String city, String country, Boolean isRead) {
        this.coordinates = coordinates;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format(
                "Location[id=%d, coordinates=[%d,%d], city='%s', country='%s']",
                id, coordinates.get(0), coordinates.get(1), city, country
        );
    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Tuple getCoordinates() {
        return coordinates;
    }
}
