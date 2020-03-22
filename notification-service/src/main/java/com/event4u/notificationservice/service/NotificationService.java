package com.event4u.notificationservice.service;

import com.event4u.notificationservice.exceptionHandler.NotificationNotFoundException;
import com.event4u.notificationservice.model.Events;
import com.event4u.notificationservice.model.Notification;
import com.event4u.notificationservice.model.User;
import com.event4u.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EventsService eventsService;

    public List<Notification> findAll() {
        Iterable<Notification> allNotifications = notificationRepository.findAll();
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        allNotifications.forEach(e -> notifications.add(e));
        return notifications;
    }

    public List<Notification> findByUserId(Long id) {
        User user = userService.getUserById(id);
        return notificationRepository.findByUser(user);
    }

    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException(id));
    }

    public List<Notification> findByUserIdRead(Long id) {
        User user = userService.getUserById(id);
        Iterable<Notification> all = notificationRepository.findByUser(user);
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        all.forEach(e -> {
            if(e.getIsRead()==true)
            notifications.add(e);
        });
        return notifications;
    }

    public List<Notification> findByUserIdNotRead(Long id) {
        User user = userService.getUserById(id);
        Iterable<Notification> all = notificationRepository.findByUser(user);
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        all.forEach(e -> {
            if(e.getIsRead()==false)
                notifications.add(e);
        });
        return notifications;
    }

    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }


    public Notification createNotification(Long userId, Long eventId, String message, Date date, boolean isRead){
        User user = userService.getUserById(userId);
        Events event = eventsService.getEventById(eventId);
        return notificationRepository.save(new Notification(user,event,message,date,isRead));
    }

    public List<Notification> findByEventId(Long id) {
        Events event = eventsService.getEventById(id);
        return notificationRepository.findByEvent(event);
    }

    public void deleteByUser(Long userId) {
        User user = userService.getUserById(userId);
        notificationRepository.deleteByUser(user);
    }
}
