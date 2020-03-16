package com.event4u.eventsservice.model;


import javafx.util.Pair;

import javax.persistence.*;
import java.awt.*;
import java.lang.reflect.Array;

@Entity
@Table(name = "Location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Point coordinates;
    private String city;
    private String country;

    protected Location(Point coordinates, String city, String country, Boolean isRead) {
        this.coordinates = new Point(0,0);
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format(
                "Location[id=%d, coordinates=[%d,%d], city='%s', country='%s']",
                id, coordinates.x, coordinates.y, city, country
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

    public Point getCoordinates() {
        return coordinates;
    }
}
