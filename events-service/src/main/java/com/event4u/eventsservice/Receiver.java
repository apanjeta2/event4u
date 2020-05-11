package com.event4u.eventsservice;

import com.event4u.eventsservice.model.MessageAMQPRes;
import com.event4u.eventsservice.service.EventService;
import com.event4u.eventsservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;
    @Autowired
    private Sender sender;

    private Long idEvent = null;

    @RabbitListener(queues = "notifications-events")
    public void listen(String in) throws JsonProcessingException {
        try {
            MessageAMQPRes mes = new ObjectMapper().readValue(in, MessageAMQPRes.class);
            System.out.println("Odgovor je:" + mes.getAckMessage());
            //TODO: logovati
        }
        catch (Exception ex) {
            //Neuspjesno kreirano - brisi event
            eventService.deleteById(idEvent);
            System.out.println("Message: " + in);
        }
    }
}
