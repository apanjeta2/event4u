package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.model.SuccessMessage;
import com.event4u.eventsservice.model.User;
import com.event4u.eventsservice.repository.UserRepository;
import com.event4u.eventsservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("events-micro/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path ="", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ArrayList<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping(path ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public User newUser(@PathVariable Long id) {
        return userService.createUser(id);
    }

    @DeleteMapping(path ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessMessage deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new SuccessMessage("User with id " + id + " successfully deleted");
    }
}
