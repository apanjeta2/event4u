package com.event4u.notificationservice.controller;

import com.event4u.notificationservice.model.Events;
import com.event4u.notificationservice.model.Notification;
import com.event4u.notificationservice.model.User;
import com.event4u.notificationservice.service.EventsService;
import com.event4u.notificationservice.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequestMapping(path="users",produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> allUsers() {
            return userService.findAll();
    }

    //Vraca user po id-u
    @GetMapping("/getById/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    //Dodavanje user-a
    @PostMapping("")
    public User newUser(@RequestBody String id) {
        return userService.createUser(Long.parseLong(id));
    }

    @PostMapping("subscribeTo")
    public ResponseEntity<JSONObject> subscribeTo(@RequestHeader("Authorization") String header, @RequestBody String idS){

        //Iz headrea dobija token i odatle generisemo id
        Long id1= Long.valueOf(12);//user koji se subscribe-a dobijen iz tokena????
        Long id2= Long.parseLong(idS); //User na kojeg se subscribe

        //Upisivanje u bazu subscribera
        userService.addSubscriber(id1,id2);
        JSONObject Entity = new JSONObject();
        Entity.put("message", "Subscribed to user "+header );
        return  new ResponseEntity<JSONObject>(
                Entity,
                HttpStatus.OK);
    }
    @GetMapping("getSubscribers")
    public Set<User> getSubscribers(@RequestHeader("Authorization") String header, @RequestBody String idS) {

        //Iz headrea dobija token i odatle generisemo id
        Long id1 = Long.valueOf(idS);//user koji se subscribe-a dobijen iz tokena????
        return userService.getSubscribers(id1);
    }
    //Brisanje subscribera po id-u
    @DeleteMapping("/subscriber/{id}")
    public ResponseEntity<JSONObject> deleteSubscriber(@PathVariable Long id) {
        try {
            Long id1 = Long.valueOf(12);
            userService.deleteSubscriber(id1, id);
            JSONObject Entity = new JSONObject();
            Entity.put("message","Successful deletion of the subscriber with id: "+id );
            return  new ResponseEntity<JSONObject>(
                    Entity,
                    HttpStatus.OK);
        }
        catch(Exception e) {
            JSONObject Entity2 = new JSONObject();
            Entity2.put("message","Error deleting subsriber with id: "+id );

            return  new ResponseEntity<JSONObject>(
                    Entity2,
                    HttpStatus.BAD_REQUEST);
        }

    }
    //Brisanje usera po id-u
    @DeleteMapping("/{id}")
    public ResponseEntity<JSONObject> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            JSONObject Entity = new JSONObject();
            Entity.put("message","Successful deletion of the user with id: "+id );
            return  new ResponseEntity<JSONObject>(
                    Entity,
                    HttpStatus.OK);
        }
        catch(Exception e) {
            JSONObject Entity2 = new JSONObject();
            Entity2.put("message","Error deleting user with id: "+id );

            return  new ResponseEntity<JSONObject>(
                    Entity2,
                    HttpStatus.BAD_REQUEST);
        }

    }
}

