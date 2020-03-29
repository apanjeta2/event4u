package com.event4u.notificationservice.service;

import com.event4u.notificationservice.exception.UserNotFoundException;
import com.event4u.notificationservice.model.User;
import com.event4u.notificationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        Iterable<User> allUsers = userRepository.findAll();
        ArrayList<User> u = new ArrayList<User>();
        allUsers.forEach(e -> u.add(e));
        return u;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User createUser(Long userId){
        return userRepository.save(new User(userId));
    }
}
