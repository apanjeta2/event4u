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
    SuccessMessage markEventInterested(@PathVariable Long idEvent, @RequestBody Long idUser) {
        eventUserService.markAsInterested(idUser, idEvent);
        return new SuccessMessage("Event successfully marked as interested");
    }

    @PostMapping(path = "/going/{idEvent}", produces = {MediaType.APPLICATION_JSON_VALUE})
    SuccessMessage markEventGoing(@PathVariable Long idEvent, @RequestBody Long idUser) {
        eventUserService.markAsGoing(idUser, idEvent);
        return new SuccessMessage("Event successfully marked as going");
    }

    @DeleteMapping(path = "/removeMark/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    SuccessMessage deleteMark(@PathVariable Long idEvent, @RequestBody Long idUser) {
        eventUserService.removeMark(idEvent, idUser);
        return new SuccessMessage("Mark successfully removed");
    }
}
