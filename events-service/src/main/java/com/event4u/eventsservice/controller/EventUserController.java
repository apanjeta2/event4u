package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.model.SuccessMessage;
import com.event4u.eventsservice.model.User;
import com.event4u.eventsservice.service.EventUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("events-micro")
@RestController
public class EventUserController {
    @Autowired
    private EventUserService eventUserService;

    @GetMapping(path = "/going/{idEvent}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUsersGoing(@PathVariable Long idEvent) {
        return eventUserService.getUsersGoing(idEvent);
    }

    @GetMapping(path = "/interested/{idEvent}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUsersInterested(@PathVariable Long idEvent) {
        return eventUserService.getUsersInterested(idEvent);
    }

    @GetMapping(path = "/goingNum/{idEvent}", produces = {MediaType.APPLICATION_JSON_VALUE})
    int getNumberOfUsersGoing(@PathVariable Long idEvent) {
        return eventUserService.getNumberOfUsersGoing(idEvent);
    }

    @GetMapping(path = "/interestedNum/{idEvent}", produces = {MediaType.APPLICATION_JSON_VALUE})
    int getNumberOfUsersInterested(@PathVariable Long idEvent) {
        return eventUserService.getNumberOfUsersInterested(idEvent);
    }

    @PostMapping(path = "/interested/{idEvent}", produces = {MediaType.APPLICATION_JSON_VALUE})
    SuccessMessage markEventInterested(@RequestHeader("Authorization") String token, @PathVariable Long idEvent) {
        eventUserService.markAsInterested(idEvent, token);
        return new SuccessMessage("Event successfully marked as interested");
    }

    @PostMapping(path = "/going/{idEvent}", produces = {MediaType.APPLICATION_JSON_VALUE})
    SuccessMessage markEventGoing(@RequestHeader("Authorization") String token, @PathVariable Long idEvent) {
        eventUserService.markAsGoing(idEvent, token);
        return new SuccessMessage("Event successfully marked as going");
    }

    @DeleteMapping(path = "/removeMark/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    SuccessMessage deleteMark(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        eventUserService.removeMark(id, token);
        return new SuccessMessage("Mark successfully removed");
    }
}
