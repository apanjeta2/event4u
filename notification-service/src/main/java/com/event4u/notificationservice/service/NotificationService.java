package com.event4u.notificationservice.service;

import com.event4u.notificationservice.ServiceInstanceRestController;
import com.event4u.notificationservice.controller.MyStompSessionHandler;
import com.event4u.notificationservice.exception.NotificationNotFoundException;
import com.event4u.notificationservice.model.*;
import com.event4u.notificationservice.repository.NotificationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.xml.bind.DatatypeConverter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

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

    public void validateToken(String token, String key) {


        String token1=token.replace("Bearer ","");
        String base64Key = DatatypeConverter.printBase64Binary(key.getBytes());
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64Key);

        Claims claim = Jwts.parser().setSigningKey(secretBytes).parseClaimsJws(token1).getBody();

    }
    public Long getUserIdFromToken(String token, String key) {


        String token1=token.replace("Bearer ","");
        String base64Key = DatatypeConverter.printBase64Binary(key.getBytes());
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64Key);
        Claims claim = Jwts.parser().setSigningKey(secretBytes).parseClaimsJws(token1).getBody();
        ObjectMapper mapper = new ObjectMapper();

        UserBody u = mapper.convertValue(claim, UserBody.class);

        Long id1=u.getId();
        return id1;
    }

    public void sendToSubscribers(String token) throws ExecutionException, InterruptedException {

        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));
        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        String url = "ws://localhost:8088/ws";
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        StompSession session = stompClient.connect(url, sessionHandler).get();
        session.subscribe("topic", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Notification.class;
            }

            @Override
            public void handleFrame(StompHeaders headers,Object payload) {
                System.err.println(payload.toString());
            }
        });
        session.send("/app/hello", token);
    }
    public Object findAll(String token, String key) throws ExecutionException, InterruptedException {


        User user = userService.getUserById(token ,key,getUserIdFromToken(token, key));
        return notificationRepository.findByUser(user);
    }

    public List<Notification> findByUserId(String token, String key, Long id) {

        User user = userService.getUserById(token ,key,id);
        return notificationRepository.findByUser(user);
    }

    public Notification findById(String token, String key, Long id) {
        validateToken(token, key);
        return notificationRepository.findById(id).orElseThrow(() -> new NotificationNotFoundException(id));
    }

    public List<Notification> findByUserIdRead(String token, String key, Long id) {
        validateToken(token, key);
        User user = userService.getUserById(token, key,id);
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

    public List<Notification> findByUserIdNotRead(String token, String key,Long id) {

        User user = userService.getUserById(token ,key,id);
        Iterable<Notification> all = notificationRepository.findByUser(user);
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        all.forEach(e -> {
            if(e.getIsRead()==false)
                notifications.add(e);
        });
        return notifications;
    }

    public void deleteById(String token , String key, Long id) {
        validateToken(token, key);
        notificationRepository.deleteById(id);
    }


    public Notification createNotification(String token, String key, Long userId, Long eventId, String message, LocalDate date, boolean isRead, int type) throws ExecutionException, InterruptedException {

        sendToSubscribers(token);
        User user = userService.getUserById(token, key,userId);
        Events event = eventsService.getEventById(token, key, eventId);
        return notificationRepository.save(new Notification(user,event,message,date,isRead, type));
    }

    public Notification createNotificationNew(String token, NotificationBody not, String key, int type) throws ExecutionException, InterruptedException {
        //parse token
        sendToSubscribers(token);
        token=token.replace("Bearer ","");
        String base64Key = DatatypeConverter.printBase64Binary(key.getBytes());
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64Key);
        Claims claim = Jwts.parser().setSigningKey(secretBytes).parseClaimsJws(token).getBody();

        //Kreiranje poruke

        String message = "{\"event\": \""+not.getName() +"\" , \"date\": \""+not.getDate()+"\""+" , \"name\": \"\"}";

        ObjectMapper mapper = new ObjectMapper();

        //Iz tokena dobijemo info o useru
        UserBody u = mapper.convertValue(claim, UserBody.class);
        //return claim;

        Long userid=u.getId();
        //Doadace se user ako ne postoji jer se kupi sa user managment servisa
        userService.createUser(token, key, userid);

        //Tip 1 notif. je za kreiranje notifikacija za jednog uera, npr kad se neko subscribe na njegov event dobije notifikaciju
        if (type==1)
        return createNotification(token, key, userid, not.getEventId(), message, not.getDate(), false, type);
        //Tip 2 notif. je za kreiranje notifikacija za sve subscribere usera koji je kreirao event
        else //salji samo subscriberima
        {

            Set<Long> all= userService.getSubscribers(token, key, userid);

            String finalToken = token;
            all.forEach(e -> {
                try {
                    createNotification(finalToken, key, e, not.getEventId(), message, not.getDate(), false, type);
                } catch (ExecutionException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            });

            return createNotification(token, key, userid, not.getEventId(), message, not.getDate(), false, type);
        }
    }

    public List<Notification> findByEventId(String token, String key, Long id) {
        Events event = eventsService.getEventById(token, key,id);
        return notificationRepository.findByEvent(event);
    }

    public void deleteByUser(String token, String key, Long userId) {

        User user = userService.getUserById(token, key, userId);
        List<Notification> lista = notificationRepository.findByUser(user);
        notificationRepository.deleteAll(lista);
    }
    public Notification updateNotification(String token, String key, Long id, NotificationBody tijelo) {
        Events e =eventsService.getEventById(token, key, tijelo.getEventId());
        LocalDate ld = tijelo.getDate();

        String message = "{\"event\": \""+tijelo.getName() +"\" , \"date\": \""+tijelo.getDate()+"\""+" , \"name\": \"\"}";

        Notification n = notificationRepository.findById(id).map(us -> {
            us.setEvent(e);
            us.setMessage(message);
            us.setDate(ld);
            return notificationRepository.save(us);
        }).orElseThrow();
        return n;
    }

    public Object updateNotificationRead(String token, String key, Long id) throws ExecutionException, InterruptedException {

        Notification n = notificationRepository.findById(id).map(us -> {
            us.setIsRead(true);
            return notificationRepository.save(us);
        }).orElseThrow();
        return findAll(token, key);
    }
}
