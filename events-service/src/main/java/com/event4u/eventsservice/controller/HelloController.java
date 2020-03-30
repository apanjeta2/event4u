package com.event4u.eventsservice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
    @RequestMapping(path ="/", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String index() {
        return "Greetings from Events microservice!";
    }

}