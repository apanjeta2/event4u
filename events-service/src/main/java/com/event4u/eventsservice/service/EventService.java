package com.event4u.eventsservice.service;

import com.event4u.eventsservice.model.Category;
import com.event4u.eventsservice.model.Event;
import com.event4u.eventsservice.model.Location;
import com.event4u.eventsservice.model.User;
import com.event4u.eventsservice.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public void deleteById(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public Event createEvent(String title, String address, LocalDate date, String description, Long idCategory, Long idUser, Long idLocation){
        Category category = categoryService.findById(idCategory);
        User crator = userService.getUserById(idUser);
        Location location = locationService.findById(idLocation);
        Event e = new Event(title,address,date,description,Boolean.TRUE, category, crator, location);
        eventRepository.save(e);
        return e;
    }

    public Event updateEvent(Long id, String title, String address, LocalDate date, String description, Boolean isActive, Long idCategory, Long idLocation) {
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
        return e;
    }
}
