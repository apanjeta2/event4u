package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.model.Category;
import com.event4u.eventsservice.model.Location;
import com.event4u.eventsservice.model.SuccessMessage;
import com.event4u.eventsservice.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("events-micro/locations")
@RestController
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping(path ="", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Location> allLocations() {
        return locationService.findAll();
    }

    @GetMapping(path ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Location getLocationById(@PathVariable Long id) {
        return locationService.findById(id);
    }

    @GetMapping(path ="/count", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Long count() {
        return locationService.count();
    }

    @PostMapping(path = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    Location newLocation(@RequestBody Location location) {
        return locationService.addNewLocation(location.getCoordinates(), location.getCity(), location.getCountry());
    }

    @DeleteMapping(path ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessMessage deleteLocation(@PathVariable String id) {
        Long userId = Long.parseLong(id);
        locationService.deleteById(userId);
        return new SuccessMessage("Location with id " + id + " successfully deleted");
    }

    @PutMapping(path ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Location updateLocation(@RequestBody Location location, @PathVariable Long id)  {
        return locationService.updateLocation(id, location.getCoordinates(), location.getCity(), location.getCountry());

    }

}

