package com.event4u.notificationservice.controller;

import com.event4u.notificationservice.model.Events;
import com.event4u.notificationservice.model.Notification;
import com.event4u.notificationservice.model.User;
import com.event4u.notificationservice.service.EventsService;
import com.event4u.notificationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping(path="users",produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> allUsers() {
            return userService.findAll();
    }

    //Vraca user po id-u
    @GetMapping("/getById/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    //Dodavanje user-a
    @PostMapping("")
    public User newUser(@RequestBody String id) {
        return userService.createUser(Long.parseLong(id));
    }

}

