package com.event4u.notificationservice.controller;

import com.event4u.notificationservice.model.Events;
import com.event4u.notificationservice.service.EventsService;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path="events",produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class EventController {

    @Autowired
    private EventsService eventService;
    //@Autowired
    //private DiscoveryClient discoveryClient;

    //Vraca event po id-u
    @GetMapping(path="/getById/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public Events getEventById(@PathVariable Long id){
        return eventService.getEventById(id);
    }
    @GetMapping(path="",produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object allEvents() {
        return eventService.findAll();
    }

    //Dodavanje event-a
    @PostMapping(path="",produces = {MediaType.APPLICATION_JSON_VALUE})
    public Events newEvent(@RequestBody String eventId) {
        return eventService.createEvent(Long.parseLong(eventId));
    }
}
