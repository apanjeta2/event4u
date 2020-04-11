package com.event4u.notificationservice.controller;

import com.event4u.notificationservice.NotificationServiceApplication;
import com.event4u.notificationservice.ServiceInstanceRestController;
import com.event4u.notificationservice.jwt.JwtTokenUtil;
import com.event4u.notificationservice.model.*;
import com.event4u.notificationservice.service.EventsService;
import com.event4u.notificationservice.service.NotificationService;
import com.event4u.notificationservice.service.UserService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import net.minidev.json.JSONObject;
import netscape.javascript.JSObject;
import org.apache.tomcat.util.http.parser.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequestMapping(path="/notifications",produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class NotificationController {

    private static final Logger log =
            LoggerFactory.getLogger(NotificationServiceApplication.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventsService eventService;
    @Autowired
    private ServiceInstanceRestController serviceInstanceRestController;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("")
    public Object allNotifications() {
        return notificationService.findAll();
    }
    //Vraca sve notifikacije za jednog korisnika
    @GetMapping("/getByUserId/{id}")
    public List<Notification> getNotificationsByUserId(@PathVariable Long id) {
        return notificationService.findByUserId(id);
    }

    //Vraca sve notifikacije za jednog korisnika koje su procitane
    @GetMapping("/getByUserIdRead/{id}")
    public List<Notification> getNotificationsByUserIdRead(@PathVariable Long id) {
        return notificationService.findByUserIdRead(id);
    }

    //Vraca sve notifikacije za jednog korisnika koje nisu procitane
    @GetMapping("/getByUserIdNotRead/{id}")
    public List<Notification> getNotificationsByUserIdNotRead(@PathVariable Long id) {
        return notificationService.findByUserIdNotRead(id);
    }

    //Vraca notifikaciju po id-u
    @GetMapping("/getById/{id}")
    public Notification getNotificationsById(@PathVariable Long id) {
        return notificationService.findById(id);
    }

    //Vraca sve notifikacije za jednog korisnika
    @GetMapping("/getByEventId/{id}")
    public List<Notification> getNotificationsByEventId(@PathVariable Long id) {
        return notificationService.findByEventId(id);
    }

    //Brisanje notifikacije po id-u
    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteById(id);
            JSONObject Entity = new JSONObject();
            Entity.put("message","Successful deletion of the notification with id: "+id );
            return  new ResponseEntity<JSONObject>(
                    Entity,
                    HttpStatus.OK);
        }
        catch(Exception e) {
            JSONObject Entity2 = new JSONObject();
            Entity2.put("message","Error deleting notifications with id: "+id );

            return  new ResponseEntity<JSONObject>(
                    Entity2,
                    HttpStatus.BAD_REQUEST);
        }

    }

    //Kreiranje nove notifikacije
    @PostMapping("")
    public Object newNotification(@RequestParam Long userId, @RequestParam Long eventId, @RequestParam String message, @RequestParam String date, @RequestParam boolean isRead) throws ParseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        //convert String to LocalDate
        LocalDate dateL = LocalDate.parse(date, formatter);
        return notificationService.createNotification(userId, eventId, message, dateL, isRead,1);
    }

    @Value("${jwt.secret}")
    private String key;
    //Kreiranje nove notifikacije sa body
    @PostMapping(path="/postNotification", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object postNewNotification(@RequestHeader("Authorization") String token, @RequestBody NotificationBody not) {
       return notificationService.createNotificationNew(token, not, key,2);
    }


      //Brisanje svih notifikacija jednog korisnika
    @DeleteMapping("/deleteByUserId/{id}")
    public ResponseEntity<JSONObject> deleteNotificationByUser(@PathVariable Long id) {
        try {
            notificationService.deleteByUser(id);
            JSONObject Entity = new JSONObject();
            Entity.put("message","Successful deletion of the notification with user id: "+id );
            return  new ResponseEntity<JSONObject>(
                    Entity,
                    HttpStatus.OK);
        }
        catch(Exception e) {
            JSONObject Entity2 = new JSONObject();
            Entity2.put("message","Error deleting notifications with user id: "+id );

            return  new ResponseEntity<JSONObject>(
                    Entity2,
                    HttpStatus.BAD_REQUEST);
        }
    }

    //Update notification
    @PutMapping(path ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Notification updateNotification(@RequestBody NotificationBody tijelo, @PathVariable Long id) {
        return notificationService.updateNotification(id,tijelo);
    }

    @PostMapping(path ="/createGoingTo/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public NotificationBody createGoingNotification(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        Events e = eventService.getEventById(id);
        NotificationBody not= new NotificationBody(id, e.getName(), e.getDate());
        Notification n = notificationService.createNotificationNew(token, not, key, 1);
        return not;
    }

}
