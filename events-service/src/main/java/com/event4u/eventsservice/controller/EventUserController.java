package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.model.User;
import com.event4u.eventsservice.service.EventUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("events-micro")
@RestController
public class EventUserController {
    @Autowired
    private EventUserService eventUserService;

    //TODO: fix error when id not valid
    @GetMapping("/going/{idEvent}")
    public List<User> getUsersGoing(@PathVariable Long idEvent) {
        return eventUserService.getUsersGoing(idEvent);
    }

    @GetMapping("/interested/{idEvent}")
    public List<User> getUsersInterested(@PathVariable Long idEvent) {
        return eventUserService.getUsersInterested(idEvent);
    }

    @GetMapping("/goingNum/{idEvent}")
    int getNumberOfUsersGoing(@PathVariable Long idEvent) {
        return eventUserService.getNumberOfUsersGoing(idEvent);
    }

    @GetMapping("/interestedNum/{idEvent}")
    int getNumberOfUsersInterested(@PathVariable Long idEvent) {
        return eventUserService.getNumberOfUsersInterested(idEvent);
    }

    @PostMapping("/interested/{idEvent}")
    void markEventInterested(@PathVariable Long idEvent, @RequestBody Long idUser) {
        eventUserService.markAsInterested(idUser, idEvent);
    }

    @PostMapping("/going/{idEvent}")
    void markEventGoing(@PathVariable Long idEvent, @RequestBody Long idUser) {
        eventUserService.markAsGoing(idUser, idEvent);
    }

    //TODO: Doraditi
    @DeleteMapping("/users/{id}")
    void deleteMark(@PathVariable Long id) {
        eventUserService.removeMark(id);
    }
}
