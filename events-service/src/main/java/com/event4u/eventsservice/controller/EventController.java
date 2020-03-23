package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.model.Event;
import com.event4u.eventsservice.model.NewEvent;
import com.event4u.eventsservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RequestMapping("events-micro/events")
@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("")
    public List<Event> allEvents() {
        return eventService.findAll();
    }

    @GetMapping("/count")
    public Long count() {
        return eventService.count();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    //TODO: FIx this!
    @PostMapping("")
    public Event newEvent(@RequestBody NewEvent event) {
        return eventService.createEvent(event.getTitle(), event.getAddress(), event.getDate() ,event.getDescription() ,event.getIdCategory(), event.getIdCreator(), event.getIdLocation());
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable String id) {
        Long userId = Long.parseLong(id);
        eventService.deleteById(userId);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@RequestBody NewEvent event, @PathVariable Long id) {
        return eventService.updateEvent(id, event.getTitle(),event.getAddress(), event.getDate(), event.getDescription(), event.getActive(), event.getIdCategory(), event.getIdLocation());
    }


}
