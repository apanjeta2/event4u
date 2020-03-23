package com.event4u.eventsservice.service;

import com.event4u.eventsservice.model.Location;
import com.event4u.eventsservice.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> findAll() {
        var it = locationRepository.findAll();
        var locations = new ArrayList<Location>();
        it.forEach(e -> locations.add(e));
        return locations;
    }

    public Location findById(Long id) {
        return locationRepository.findById(id).orElseThrow();
    }

    public Long count() {
        return locationRepository.count();
    }

    public void deleteById(Long locationId) {
        locationRepository.deleteById(locationId);
    }

    public Location addNewLocation(Point coordinates, String city, String country) {
        return locationRepository.save(new Location(coordinates, city, country));
    }

    public Optional<Location> updateLocation(Long id, Point coordinates, String city, String country) {
        return locationRepository.findById(id).map(location-> {
            location.setCoordinates(coordinates);
            location.setCity(city);
            location.setCountry(country);
            return locationRepository.save(location);
        });
    }
}
