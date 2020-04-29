package com.event4u.notificationservice.service;

import com.event4u.notificationservice.ServiceInstanceRestController;
import com.event4u.notificationservice.exception.NotificationNotFoundException;
import com.event4u.notificationservice.model.*;
import com.event4u.notificationservice.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EventsService eventsService;
    @Autowired
    private ServiceInstanceRestController serviceInstanceRestController;

    public Object findAll() {
        Iterable<Notification> allNotifications = notificationRepository.findAll();
        return allNotifications;
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
    public Iterable<Notification> findAllByEvent(String toke, String key,Long id) {
        Events event = eventsService.getEventById(toke, key,id);
        Iterable<Notification> all = notificationRepository.findByEvent(event);

        return all;
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


    public Notification createNotification(String token, String key, Long userId, Long eventId, String message, LocalDate date, boolean isRead, int type){
        User user = userService.getUserById(userId);
        Events event = eventsService.getEventById(token, key,eventId);
        return notificationRepository.save(new Notification(user,event,message,date,isRead, type));
    }

    public Notification createNotificationNew(String token, NotificationBody not, String key, int type) {
        //parse token
        token=token.replace("Bearer ","");
        String base64Key = DatatypeConverter.printBase64Binary(key.getBytes());
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64Key);
        Claims claim = Jwts.parser().setSigningKey(secretBytes).parseClaimsJws(token).getBody();


        //Kreiranje poruke

        String message = "{\"event\": \""+not.getName() +"\" , \"date\": \""+not.getDate()+"\""+"\" , \"name\": \"\"}";

        ObjectMapper mapper = new ObjectMapper();

        //Iz tokena dobijemo info o useru
        UserBody u = mapper.convertValue(claim, UserBody.class);
        //return claim;

        Long userid=u.getId();
        //Doadace se user ako ne postoji jer se kupi sa user managment servisa
        userService.createUser(userid);
        if (type==1)
        return createNotification(token, key,userid, not.getEventId(), message, not.getDate(), false, type);
        else //salji samo subscriberima
        {
            Set<Long> all= userService.getSubscribers(userid);
            String finalToken = token;
            all.forEach(e -> {
                createNotification(finalToken, key, e, not.getEventId(), message, not.getDate(), false, type);
            });
            return createNotification(token, key,userid, not.getEventId(), message, not.getDate(), false, type);
        }
    }

    public List<Notification> findByEventId(String token, String key, Long id) {
        Events event = eventsService.getEventById(token, key,id);
        return notificationRepository.findByEvent(event);
    }

    public void deleteByUser(Long userId) {
        User user = userService.getUserById(userId);
        List<Notification> lista = notificationRepository.findByUser(user);
        notificationRepository.deleteAll(lista);
    }
    public Notification updateNotification(String token, String key, Long id, NotificationBody tijelo) {
        Events e =eventsService.getEventById(token, key, tijelo.getEventId());
        LocalDate ld = tijelo.getDate();

        String message = "{\"event\": \""+tijelo.getName() +"\" , \"date\": \""+tijelo.getDate()+"\""+"\" , \"name\": \"\"}";

        Notification n = notificationRepository.findById(id).map(us -> {
            us.setEvent(e);
            us.setMessage(message);
            us.setDate(ld);
            return notificationRepository.save(us);
        }).orElseThrow();
        return n;
    }
}
