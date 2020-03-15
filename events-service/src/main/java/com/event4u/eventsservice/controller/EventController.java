package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.repository.EventInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("event")
@RestController
public class EventController {
    @Autowired
    private EventInterface eventRepository;
}
