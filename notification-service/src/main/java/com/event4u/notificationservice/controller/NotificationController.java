package com.event4u.notificationservice.controller;

import com.event4u.notificationservice.model.Notification;
import com.event4u.notificationservice.service.NotificationService;
import net.minidev.json.JSONObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping(path="/notifications",produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

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

        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        return notificationService.createNotification(userId, eventId, message, date1, isRead);


    }
      //Brisanje svih notifikacija jednog korisnika
    @DeleteMapping("/deleteByUserId/{id}")
    public ResponseEntity<JSONObject> deleteNotificationByUser(@PathVariable Long id) {
        try {
            notificationService.deleteByUser(id);
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

}
