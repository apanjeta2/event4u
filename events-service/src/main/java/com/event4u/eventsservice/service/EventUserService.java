package com.event4u.eventsservice.service;

import com.event4u.eventsservice.model.Event;
import com.event4u.eventsservice.model.EventUser;
import com.event4u.eventsservice.model.User;
import com.event4u.eventsservice.repository.EventUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventUserService {
    @Autowired
    private EventUserRepository eventUserRepository;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private  NotificationHelperService notificationHelperService;

    private List<EventUser> getAllUsers(Long id){
        var it = eventUserRepository.findAll();
        var users = new ArrayList<EventUser>();
        it.forEach(eventUser -> {
            if (eventUser.getEvent().getId().equals(id)) {
                users.add(eventUser);
            }
        });
        return users;
    }

    public List<User> getUsersGoing(Long id) {
        List<EventUser> allEventUsers = getAllUsers(id);
        allEventUsers.removeIf(user -> user.getGoing()!=Boolean.TRUE);
        ArrayList<User> users = new ArrayList<>();
        allEventUsers.forEach(eventUser -> users.add(eventUser.getUser()));
        return users;
    }

    public List<User> getUsersInterested(Long id) {
        List<EventUser> allEventUsers = getAllUsers(id);
        allEventUsers.removeIf(user -> user.getGoing()==Boolean.TRUE);
        ArrayList<User> users = new ArrayList<>();
        allEventUsers.forEach(eventUser -> users.add(eventUser.getUser()));
        return users;
    }

    public int getNumberOfUsersGoing(Long id) {
        return getUsersGoing(id).size();
    }

    public int getNumberOfUsersInterested(Long id) {
        return getUsersInterested(id).size();
    }

    private void markNew(User user, Event event, Boolean isGoing) {
        eventUserRepository.save(new EventUser(user, event, isGoing));
    }

    public void markAsGoing(Long idUser, Long idEvent, String token) {
        EventUser eu = null;
        List<EventUser> allEventUsers = getAllUsers(idEvent);
        for (EventUser eventUser : allEventUsers) {
            if (eventUser.getId().equals(idUser)) {
                eu = eventUser;
                break;
            }
        }
        Event event = eventService.findById(idEvent);
        User user = userService.getUserById(idUser);
        if (eu == null) {
            markNew(user, event, Boolean.TRUE);
        }
        //notificationHelperService.createGoingToNotificaion(idEvent, token); TODO: fix this
    }

    public void markAsInterested(Long idUser, Long idEvent) {
        EventUser eu = null;
        List<EventUser> allEventUsers = getAllUsers(idEvent);
        for (EventUser eventUser : allEventUsers) {
            if (eventUser.getId().equals(idUser)) {
                eventUser.setGoing(Boolean.FALSE);
                break;
            }
        }
        Event event = eventService.findById(idEvent);
        User user = userService.getUserById(idUser);
        if (eu == null) {
            markNew(user, event, Boolean.FALSE);
        }
    }

    public void removeMark(Long idUser, Long idEvent) {
        EventUser eu = null;
        List<EventUser> allEventUsers = getAllUsers(idEvent);
        for (EventUser eventUser : allEventUsers) {
            if (eventUser.getId().equals(idUser)) {
                eventUserRepository.deleteById(eventUser.getId());
                break;
            }
        }
    }

}
