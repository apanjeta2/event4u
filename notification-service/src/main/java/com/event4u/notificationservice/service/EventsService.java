package com.event4u.notificationservice.service;

import com.event4u.notificationservice.exception.EventNotFoundException;
import com.event4u.notificationservice.model.Events;
import com.event4u.notificationservice.repository.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventsService {
    @Autowired
    private EventsRepository eventsRepository;

    public List<Events> findAll() {
        Iterable<Events> allEvents = eventsRepository.findAll();
        ArrayList<Events> events = new ArrayList<Events>();
        allEvents.forEach(e -> events.add(e));
        return events;
    }

    public Optional<Events> findById(Long id) {
        return eventsRepository.findById(id);
    }

    public Events getEventById(Long id) {
        return eventsRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
    }

    public Events createEvent(Long eventId){
        return eventsRepository.save(new Events(eventId));
    }
    public Events createEventNew(Long eventId, String name, LocalDate date){
        Events e =new Events(eventId);
        e.setName(name);
        e.setDate(date);
        return eventsRepository.save(e);
    }
    public void deleteById(Long id) {
        eventsRepository.deleteById(id);
    }
    public Events updateEvent(Long id, String name, LocalDate date) {
        Events e = eventsRepository.findById(id).map(event -> {
            event.setEventId(id);
            event.setName(name);
            event.setDate(date);
            //event.setDate(date);
            return eventsRepository.save(event);
        }).orElseThrow();
        return e;
    }
}
