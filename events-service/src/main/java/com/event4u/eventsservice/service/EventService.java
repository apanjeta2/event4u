package com.event4u.eventsservice.service;

import com.event4u.eventsservice.model.*;
import com.event4u.eventsservice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationHelperService notificationHelperService;
    @Autowired
    private DiscoveryService discoveryService;

    public List<Event> findAll() {
        var it = eventRepository.findAll();
        var events = new ArrayList<Event>();
        it.forEach(e -> events.add(e));
        return events;
    }

    public Event findById(Long id) {
        Event e = eventRepository.findById(id).orElseThrow();
        return e;
    }

    public Long count() {
        return eventRepository.count();
    }

    public void deleteById(Long eventId, String token) {
        eventRepository.deleteById(eventId);
        notificationHelperService.deleteEventNotifications(eventId, token); //TODO: fix this
    }

    public Event createEvent(String title, String address, LocalDate date, String description, Long idCategory, Long idUser, Long idLocation, String token){
        Category category = categoryService.findById(idCategory);
        User crator = userService.getUserById(idUser);
        Location location = locationService.findById(idLocation);
        Event e = new Event(title,address,date,description,Boolean.TRUE, category, crator, location);
        eventRepository.save(e);
        //notificationHelperService.getEvents(token);
        notificationHelperService.createEventNotifications(e, token); //TODO: fix this
        return e;
    }

    public Event updateEvent(Long id, String title, String address, LocalDate date, String description, Boolean isActive, Long idCategory, Long idLocation, String token) {
        Event e = eventRepository.findById(id).map(event -> {
            event.setActive(isActive);
            event.setAddress(address);
            event.setTitle(title);
            event.setDate(date);
            event.setDescription(description);
            if (event.getCategory().getId() != idCategory) {
                Category c = categoryService.findById(idCategory);
                event.setCategory(c);
            }
            if (event.getLocation().getId() != idLocation) {
                Location l = locationService.findById(idLocation);
                event.setLocation(l);
            }
            return eventRepository.save(event);
        }).orElseThrow();
        notificationHelperService.updateEventNotifications(e, token); //TODO:fix this
        return e;
    }
}
