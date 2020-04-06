package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.model.Event;
import com.event4u.eventsservice.model.NewEvent;
import com.event4u.eventsservice.model.SuccessMessage;
import com.event4u.eventsservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RequestMapping("events-micro/events")
@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping(path ="", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Event> allEvents() {
        return eventService.findAll();
    }

    @GetMapping(path = "/count", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Long count() {
        return eventService.count();
    }

    @GetMapping(path ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Event getEventById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @PostMapping(path = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Event newEvent(@RequestHeader("Authorization") String token, @RequestBody NewEvent event) {
        return eventService.createEvent(event.getTitle(), event.getAddress(), event.getDate() ,event.getDescription() ,event.getIdCategory(), event.getIdCreator(), event.getIdLocation(), token);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessMessage deleteEvent(@RequestHeader("Authorization") String token, @PathVariable String id) {
        Long userId = Long.parseLong(id);
        eventService.deleteById(userId, token);
        return new SuccessMessage("Event with id " + id + " successfully deleted");
    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Event updateEvent(@RequestHeader("Authorization") String token, @RequestBody NewEvent event, @PathVariable Long id) {
        return eventService.updateEvent(id, event.getTitle(),event.getAddress(), event.getDate(), event.getDescription(), event.getActive(), event.getIdCategory(), event.getIdLocation(), token);
    }


}
