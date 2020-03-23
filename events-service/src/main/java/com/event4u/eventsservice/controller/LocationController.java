package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.model.Category;
import com.event4u.eventsservice.model.Location;
import com.event4u.eventsservice.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("events-micro/locations")
@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("")
    public List<Location> allLocations() {
        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable Long id) {
        return locationService.findById(id);
    }

    @GetMapping("/count")
    public Long count() {
        return locationService.count();
    }

    @PostMapping("")
    Location newLocation(@RequestBody Location location) {
        return locationService.addNewLocation(location.getCoordinates(), location.getCity(), location.getCountry());
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable String id) {
        Long userId = Long.parseLong(id);
        locationService.deleteById(userId);
    }

    @PutMapping("/{id}")
    public Location updateLocation(@RequestBody Location location, @PathVariable Long id)  {
        return locationService.updateLocation(id, location.getCoordinates(), location.getCity(), location.getCountry()).orElseThrow();

    }

}

