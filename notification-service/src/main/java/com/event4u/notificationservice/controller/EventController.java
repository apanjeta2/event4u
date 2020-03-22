package com.event4u.notificationservice.controller;

import com.event4u.notificationservice.model.Events;
import com.event4u.notificationservice.model.Notification;
import com.event4u.notificationservice.service.EventsService;
import com.event4u.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.events.Event;

import java.util.List;

@RequestMapping("events")
@RestController
public class EventController {

    @Autowired
    private EventsService eventService;

    @GetMapping("")
    public List<Events> allEvents() {
        return eventService.findAll();
    }

    //Dodavanje event-a
    @PostMapping("")
    public Events newEvent(@RequestParam Long eventId) {
        return eventService.createEvent(eventId);
    }
}
