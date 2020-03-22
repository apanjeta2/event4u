package com.event4u.notificationservice.service;

import com.event4u.notificationservice.exceptionHandler.EventNotFoundException;
import com.event4u.notificationservice.model.Events;
import com.event4u.notificationservice.model.Notification;
import com.event4u.notificationservice.model.User;
import com.event4u.notificationservice.repository.EventsRepository;
import com.event4u.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
