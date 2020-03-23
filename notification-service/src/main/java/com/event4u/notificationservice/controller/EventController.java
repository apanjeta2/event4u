package com.event4u.notificationservice.controller;

import com.event4u.notificationservice.exceptionHandler.EventNotFoundException;
import com.event4u.notificationservice.model.Events;
import com.event4u.notificationservice.model.Notification;
import com.event4u.notificationservice.model.User;
import com.event4u.notificationservice.service.EventsService;
import com.event4u.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.events.Event;

import java.util.List;

@RequestMapping("events")
@RestController
public class EventController {

    @Autowired
    private EventsService eventService;

    //Vraca event po id-u
    @GetMapping("/getById/{id}")
    public Events getEventById(@PathVariable Long id){
        return eventService.getEventById(id);
    }
    @GetMapping("")
    public List<Events> allEvents() {
        try {
        return eventService.findAll();
        }
        catch(Exception e) {
            return (List<Events>) new ResponseEntity<String>(
                    "Error getting events.",
                    HttpStatus.BAD_REQUEST);
        }
    }

    //Dodavanje event-a
    @PostMapping("")
    public Events newEvent(@RequestBody String eventId) {
        return eventService.createEvent(Long.parseLong(eventId));
    }
}
