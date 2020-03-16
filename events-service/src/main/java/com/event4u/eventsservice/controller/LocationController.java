package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.repository.LocationInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("location")
@RestController
public class LocationController {
    @Autowired
    private LocationInterface locationRepository;
}

