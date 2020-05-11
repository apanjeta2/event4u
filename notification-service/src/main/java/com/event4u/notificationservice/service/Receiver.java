package com.event4u.notificationservice.service;

import com.event4u.notificationservice.NotificationServiceApplication;
import com.event4u.notificationservice.model.MessageAMQPResponse;
import com.event4u.notificationservice.model.Notification;
import com.event4u.notificationservice.model.NotificationBody;
import com.event4u.notificationservice.model.UserBody;
import com.event4u.notificationservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Receiver {

    @Autowired
    UserService userService;

    @Autowired
    private Sender sender;
    @Autowired
    EventsService eventsService;

    private Long eventID = null;

    //Receiver za upis eventa
    @RabbitListener(queues = "events-notifications")
    public void listen(String in) throws JsonProcessingException {
        try {
            //System.out.println("Primljeni event na strani notifikacija: " + in);
            //NotificationBody mes = new ObjectMapper().readValue(in, NotificationBody.class);
            JSONObject jsonObj = new JSONObject(in);
            System.out.println("Primljeni event na strane notifikacija: " + jsonObj);
            String date=jsonObj.getString("date");
            String name=jsonObj.getString("name");
            eventID= jsonObj.getLong("eventId");
            LocalDate localDate1 = LocalDate.parse(date);
            eventsService.createEventNew(eventID, name, localDate1);
            MessageAMQPResponse mes=new MessageAMQPResponse(eventID, "OK", "EVENTS-SERVICE");
            String jsonString=new ObjectMapper().writeValueAsString(mes);
            sender.sendMessage(jsonString);
        }
        catch (Exception ex) {
            MessageAMQPResponse mes=new MessageAMQPResponse(eventID, "ERROR", "EVENTS-SERVICE");
            String jsonString=new ObjectMapper().writeValueAsString(mes);
            sender.sendMessage(jsonString);
            System.out.println("Message: " + in);
        }
    }
}
