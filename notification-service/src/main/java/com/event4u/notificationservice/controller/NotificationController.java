package com.event4u.notificationservice.controller;

import com.event4u.notificationservice.NotificationServiceApplication;
import com.event4u.notificationservice.ServiceInstanceRestController;
import com.event4u.notificationservice.model.Notification;
import com.event4u.notificationservice.model.NotificationBody;
import com.event4u.notificationservice.service.NotificationService;
import com.fasterxml.jackson.annotation.JsonFormat;
import net.minidev.json.JSONObject;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RequestMapping(path="/notifications",produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class NotificationController {


    private static final Logger log =
            LoggerFactory.getLogger(NotificationServiceApplication.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ServiceInstanceRestController serviceInstanceRestController;

    @GetMapping("")
    public Object allNotifications() {
        //RestTemplate restTemplate = new RestTemplate();
        //List<String> listOfUrls = serviceInstanceRestController.serviceInstancesByApplicationName("event-service");
        //String url = listOfUrls.get(0);
        //String fooResourceUrl = url;
        //ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl , String.class);
        //return response;
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

        LocalDate date1 = LocalDate.of(2020,2,2);
        return notificationService.createNotification(userId, eventId, message, date1, isRead);

    }
    //Kreiranje nove notifikacije sa body
    @PostMapping(path="/postNotification", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object postNewNotification(@RequestHeader("Authorization") String token, @RequestBody NotificationBody not) {
        //token iz headera pretvori u id
        Long userid=Long.valueOf(12);
        //Pitaj oauth je li ok token

        //AKo jeste nastavi
        //Kreiranje poruke
        String message = "{\"event\": \""+not.getName() +"\" , \"date\": \""+not.getDate()+"\""+"\" , \"name\": \"\"+}";

        return notificationService.createNotification(userid, not.getEventId(), message, not.getDate(), false);

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
