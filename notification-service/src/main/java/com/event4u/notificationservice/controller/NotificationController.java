package com.event4u.notificationservice.controller;

import com.event4u.notificationservice.model.Notification;
import com.event4u.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/notifications")
@RestController
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("")
    public List<Notification> allNotifications() {
        try {
        return notificationService.findAll();
    }
        catch(Exception e) {
        return (List<Notification>) new ResponseEntity<String>(
                "Error getting notifications.",
                HttpStatus.BAD_REQUEST);
    }
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
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteById(id);
            return  new ResponseEntity<String>(
                    "message : Successful deletion of the notification with id: "+id,
                    HttpStatus.OK);
        }
        catch(Exception e) {
            return  new ResponseEntity<String>(
                    "Error deleting notifications.",
                    HttpStatus.BAD_REQUEST);
        }

    }

    //Kreiranje nove notifikacije
    @PostMapping("")
    public Object newNotification(@RequestParam Long userId, @RequestParam Long eventId, @RequestParam String message, @RequestParam String date, @RequestParam boolean isRead) throws ParseException {
        try {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            return notificationService.createNotification(userId, eventId, message, date1, isRead);
        }
        catch (Exception e) {
            return  new ResponseEntity<String>(
                    "Wrong parameters.",
                    HttpStatus.BAD_REQUEST);
        }

    }
      //Brisanje svih notifikacija jednog korisnika
    @DeleteMapping("/deleteByUserId/{id}")
    public ResponseEntity<String> deleteNotificationByUser(@PathVariable Long id) {
        try {
            notificationService.deleteByUser(id);
            return  new ResponseEntity<String>(
                    "message : Successful deletion of notifications with userid: "+id,
                    HttpStatus.OK);
        }
        catch(Exception e) {
            return  new ResponseEntity<String>(
                    "Error deleting notifications.",
                    HttpStatus.BAD_REQUEST);
        }

    }

}
