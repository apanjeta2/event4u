package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.grpc.Event4U;
import com.event4u.eventsservice.model.Event;
import com.event4u.eventsservice.model.NewEvent;
import com.event4u.eventsservice.model.SuccessMessage;
import com.event4u.eventsservice.service.EventService;
import com.event4u.eventsservice.service.LogActionService;
import com.event4u.eventsservice.service.TokenHelperService;
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
    @Autowired
    private LogActionService logActionService;
    @Autowired
    private TokenHelperService tokenHelperService;

    @GetMapping(path ="", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Event> allEvents() {
        logActionService.logAction(Long.valueOf("0"), Event4U.Request.ActionType.GET,"Event");
        return eventService.findAll();
    }

    @GetMapping(path ="/category/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Event> getEventsFromCategory(@PathVariable Long id) {
        logActionService.logAction(Long.valueOf("0"), Event4U.Request.ActionType.GET,"Event");
        return eventService.findByCategoryId(id);
    }

    @GetMapping(path = "/count", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Long count() {
        logActionService.logAction(Long.valueOf("0"), Event4U.Request.ActionType.GET,"Event");
        return eventService.count();
    }

    @GetMapping(path ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Event getEventById(@PathVariable Long id) {
        logActionService.logAction(Long.valueOf("0"), Event4U.Request.ActionType.GET,"Event");
        return eventService.findById(id);
    }

    @PostMapping(path = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Event newEvent(@RequestHeader("Authorization") String token, @RequestBody NewEvent event) {
        logActionService.logAction(tokenHelperService.getUserIdFromToken(token), Event4U.Request.ActionType.CREATE,"Event");
        return eventService.createEvent(event.getTitle(), event.getAddress(), event.getDate() ,event.getDescription() ,event.getIdCategory(), event.getIdLocation(), token);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessMessage deleteEvent(@RequestHeader("Authorization") String token, @PathVariable String id) {
        logActionService.logAction(tokenHelperService.getUserIdFromToken(token), Event4U.Request.ActionType.DELETE,"Event");
        Long eventId = Long.parseLong(id);
        eventService.deleteById(eventId, token);
        return new SuccessMessage("Event with id " + id + " successfully deleted");
    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Event updateEvent(@RequestHeader("Authorization") String token, @RequestBody NewEvent event, @PathVariable Long id) {
        logActionService.logAction(tokenHelperService.getUserIdFromToken(token), Event4U.Request.ActionType.UPDATE,"Event");
        return eventService.updateEvent(id, event.getTitle(),event.getAddress(), event.getDate(), event.getDescription(), event.getActive(), event.getIdCategory(), event.getIdLocation(), token);
    }


}
