package com.event4u.eventsservice.service;

import com.event4u.eventsservice.model.Event;
import com.event4u.eventsservice.model.User;
import com.event4u.eventsservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public ArrayList<User> getAllUsers() {
        var it = userRepository.findAll();
        var users = new ArrayList<User>();
        it.forEach(e -> users.add(e));
        return users;
    }
}
