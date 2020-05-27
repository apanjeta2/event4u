package com.event4u.eventsservice.service;

import com.event4u.eventsservice.exceptionHandling.NotFoundException;
import com.event4u.eventsservice.model.Event;
import com.event4u.eventsservice.model.EventMark;
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

    private List<EventUser> getAllUsers(Long idEvent){
        var it = eventUserRepository.findAll();
        var users = new ArrayList<EventUser>();
        it.forEach(eventUser -> {
            if (eventUser.getEvent().getId().equals(idEvent)) {
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

    public EventMark markAsGoing(Long idEvent, String token) {
        if (!eventService.existsById(idEvent)) {
            throw new NotFoundException("Event with id " + idEvent.toString());
        }
        Long idUser = Long.valueOf(3);//tokenHelperService.getUserIdFromToken(token);
        EventUser eu = getEventUser(idUser, idEvent);
        Event event = eventService.findById(idEvent);
        User user = userService.getUserById(idUser);
        if (eu != null) {
           eu.setGoing(Boolean.TRUE);
           eventUserRepository.save(eu);
        }
        else {
            markNew(user, event, Boolean.TRUE);
        }
        notificationHelperService.createGoingToNotificaion(idEvent, token);
        return new EventMark(event, true, true);
        //notificationHelperService.createGoingToNotificaion(idEvent, token);
    }

    public EventMark markAsInterested(Long idEvent, String token) {
        if (!eventService.existsById(idEvent)) {
            throw new NotFoundException("Event with id " + idEvent.toString());
        }
        Long idUser = tokenHelperService.getUserIdFromToken(token);
        EventUser eu = getEventUser(idUser, idEvent);
        Event event = eventService.findById(idEvent);
        User user = userService.getUserById(idUser);
        if (eu != null) {
            eu.setGoing(Boolean.FALSE);
            eventUserRepository.save(eu);
        }
        else {
            markNew(user, event, Boolean.FALSE);
        }
        return new EventMark(event, true, false);
    }

    public EventMark removeMark(Long idEvent, String token) {
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
        Event event = eventService.findById(idEvent);
        return new EventMark(event, false, false);
    }

    public EventUser getEventUser(Long idUser, Long idEvent) {
        List<EventUser> allEventUsers = getAllUsers(idEvent);
        allEventUsers.removeIf(eventUser -> eventUser.getUser().getId()!=idUser);
        if (allEventUsers == null || allEventUsers.size()==0) {
            return null;
        }
        return allEventUsers.get(0);
    }

}
