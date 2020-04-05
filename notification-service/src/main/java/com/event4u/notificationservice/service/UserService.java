package com.event4u.notificationservice.service;

import com.event4u.notificationservice.exception.UserNotFoundException;
import com.event4u.notificationservice.model.Events;
import com.event4u.notificationservice.model.User;
import com.event4u.notificationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public void addSubscriber(Long id1, Long id2) {
        User koSeSubscriba = userRepository.findById(id1).orElseThrow(() -> new UserNotFoundException(id1));
        User naKogaSeSubscriba = userRepository.findById(id2).orElseThrow(() -> new UserNotFoundException(id2));

        koSeSubscriba.getSubsribedTo().add(naKogaSeSubscriba);
        naKogaSeSubscriba.getSubscriber().add(koSeSubscriba);

        userRepository.save(koSeSubscriba);
        userRepository.save(naKogaSeSubscriba);
    }

    public Set<User> getSubscribers(Long id1) {
        User user1 = userRepository.findById(id1).orElseThrow(() -> new UserNotFoundException(id1));
        return user1.getSubscriber();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteSubscriber(Long id1, Long id2) {
        User user1 = userRepository.findById(id1).orElseThrow(() -> new UserNotFoundException(id1));
        User user2 = userRepository.findById(id1).orElseThrow(() -> new UserNotFoundException(id2));

        var lista = user1.getSubscriber();
        lista.remove(user2);
    }
    public User updateUser(Long id) {
        User e = userRepository.findById(id).map(us -> {
            us.setUserId(id);
            //event.setAddress(address);
            //event.setTitle(title);
            //event.setDate(date);
            return userRepository.save(us);
        }).orElseThrow();
        return e;
    }

}
