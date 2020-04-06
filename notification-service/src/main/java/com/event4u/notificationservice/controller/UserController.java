package com.event4u.notificationservice.controller;
import com.event4u.notificationservice.NotificationServiceApplication;
import com.event4u.notificationservice.ServiceInstanceRestController;
import com.event4u.notificationservice.model.*;
import com.event4u.notificationservice.service.EventsService;
import com.event4u.notificationservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping(path="users",produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class UserController {


    private static final Logger log =
            LoggerFactory.getLogger(NotificationServiceApplication.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceInstanceRestController serviceInstanceRestController;

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

    @Value("${jwt.secret}")
    private String key;
    @PostMapping("subscribeTo")
    public ResponseEntity<JSONObject> subscribeTo(@RequestHeader("Authorization") String token, @RequestBody String idS){

        //Iz headrea dobija token i odatle generisemo id
        //user koji se subscribe-a dobijen iz tokena????
        token=token.replace("Bearer ","");
        String base64Key = DatatypeConverter.printBase64Binary(key.getBytes());
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64Key);
        Claims claim = Jwts.parser().setSigningKey(secretBytes).parseClaimsJws(token).getBody();
        ObjectMapper mapper = new ObjectMapper();

        UserBody u = mapper.convertValue(claim, UserBody.class);
        //return claim;

        Long id1=u.getId();
        Long id2= Long.parseLong(idS); //User na kojeg se subscribe

        //Upisivanje u bazu subscribera
        userService.addSubscriber(id1,id2);
        JSONObject Entity = new JSONObject();
        Entity.put("message", "Subscribed to user "+u.getName() );
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

    //Vraća cijele objekte usera
    @GetMapping("getFullSubscribers")
    public Object getFullSubscribers(@RequestHeader("Authorization") String token) throws JsonProcessingException, ParseException {

        String token1=token.replace("Bearer ","");
        String base64Key = DatatypeConverter.printBase64Binary(key.getBytes());
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64Key);
        Claims claim = Jwts.parser().setSigningKey(secretBytes).parseClaimsJws(token1).getBody();
        ObjectMapper mapper = new ObjectMapper();

        UserBody u = mapper.convertValue(claim, UserBody.class);

        Long id1=u.getId();

        Set<User> users=userService.getSubscribers(id1);

        //Nadji adresu user managment servisa

        RestTemplate restTemplate = new RestTemplate();
        List<String> listOfUrls = serviceInstanceRestController.serviceInstancesByApplicationName("user-management-service");

        String url = listOfUrls.get(0);
        String fooResourceUrl = url;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", token);
        ResponseEntity<String> response = restTemplate.exchange(fooResourceUrl + "/api/users", HttpMethod.GET, new HttpEntity<Object>(headers), String.class);

        String odg = response.getBody();
        JSONParser parser = new JSONParser(odg);
        ArrayList<UserAll> json =(ArrayList<UserAll>)  parser.parse();


        ArrayList<UserAll> sviSubscriberi = new ArrayList<UserAll>();

        ObjectMapper MAPPER = new ObjectMapper();
        List<UserAll> ts = MAPPER.readValue(odg, MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, UserAll.class));
        //UserAll ua = mapper.convertValue(json.get(0), UserAll.class);

        for (int i=0; i<json.size(); i++) {

            int finalI = i;
            users.forEach(e -> {
                if (e.getUserId()==Long.parseLong(ts.get(finalI).getId())) {
                    sviSubscriberi.add(ts.get(finalI));
                }
            });
        }

        return sviSubscriberi;
    }
    //Brisanje subscribera po id-u
    @DeleteMapping("/subscriber/{id}")
    public ResponseEntity<JSONObject> deleteSubscriber(@PathVariable Long id) {
        try {
            //Citati iz tokena kome brisemo
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

