package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.repository.EventUserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventUserController {
    @Autowired
    private EventUserInterface eventUserRepository;
}
