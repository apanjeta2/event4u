package com.event4u.eventsservice.service;

import com.event4u.eventsservice.exceptionHandling.NotFoundException;
import com.event4u.eventsservice.model.Event;
import com.event4u.eventsservice.model.EventUser;
import com.event4u.eventsservice.model.User;
import com.event4u.eventsservice.repository.EventRepository;
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
    private TokenHelperService tokenHelperService;
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

    public List<User> getUsersGoing(Long idEvent) {
        if (!eventService.existsById(idEvent)) {
            throw new NotFoundException("Event with id " + idEvent.toString());
        }
        List<EventUser> allEventUsers = getAllUsers(idEvent);
        allEventUsers.removeIf(user -> user.getGoing()!=Boolean.TRUE);
        ArrayList<User> users = new ArrayList<>();
        allEventUsers.forEach(eventUser -> users.add(eventUser.getUser()));
        return users;
    }

    public List<User> getUsersInterested(Long idEvent) {
        if (!eventService.existsById(idEvent)) {
            throw new NotFoundException("Event with id " + idEvent.toString());
        }
        List<EventUser> allEventUsers = getAllUsers(idEvent);
        allEventUsers.removeIf(user -> user.getGoing()==Boolean.TRUE);
        ArrayList<User> users = new ArrayList<>();
        allEventUsers.forEach(eventUser -> users.add(eventUser.getUser()));
        return users;
    }

    public int getNumberOfUsersGoing(Long idEvent) {
        if (!eventService.existsById(idEvent)) {
            throw new NotFoundException("Event with id " + idEvent.toString());
        }
        return getUsersGoing(idEvent).size();
    }

    public int getNumberOfUsersInterested(Long idEvent) {
        if (!eventService.existsById(idEvent)) {
            throw new NotFoundException("Event with id " + idEvent.toString());
        }
        return getUsersInterested(idEvent).size();
    }

    private void markNew(User user, Event event, Boolean isGoing) {
        eventUserRepository.save(new EventUser(user, event, isGoing));
    }

    public void markAsGoing(Long idEvent, String token) {
        if (!eventService.existsById(idEvent)) {
            throw new NotFoundException("Event with id " + idEvent.toString());
        }
        EventUser eu = null;
        Long idUser = tokenHelperService.getUserIdFromToken(token);
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
        notificationHelperService.createGoingToNotificaion(idEvent, token);
    }

    public void markAsInterested(Long idEvent, String token) {
        if (!eventService.existsById(idEvent)) {
            throw new NotFoundException("Event with id " + idEvent.toString());
        }
        EventUser eu = null;
        Long idUser = tokenHelperService.getUserIdFromToken(token);
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

    public void removeMark(Long idEvent, String token) {
        if (!eventService.existsById(idEvent)) {
            throw new NotFoundException("Event with id " + idEvent.toString());
        }
        EventUser eu = null;
        Long idUser = tokenHelperService.getUserIdFromToken(token);
        List<EventUser> allEventUsers = getAllUsers(idEvent);
        for (EventUser eventUser : allEventUsers) {
            if (eventUser.getUser().getId().equals(idUser)) {
                eventUserRepository.deleteById(eventUser.getId());
                break;
            }
        }
    }

}
