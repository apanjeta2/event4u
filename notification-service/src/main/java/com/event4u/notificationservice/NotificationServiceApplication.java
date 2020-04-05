package com.event4u.notificationservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.event4u.notificationservice.model.Events;
import com.event4u.notificationservice.model.Notification;
import com.event4u.notificationservice.model.User;
import com.event4u.notificationservice.repository.EventsRepository;
import com.event4u.notificationservice.repository.NotificationRepository;
import com.event4u.notificationservice.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class NotificationServiceApplication {

    private static final Logger log = 
            LoggerFactory.getLogger(NotificationServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }


    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @Bean
    public CommandLineRunner demo(NotificationRepository repository, EventsRepository repository2, UserRepository repository3) {
        return (args) -> {
            // save a few customers
            repository.save(new Notification(repository3.save(new User(Long.valueOf(12))), repository2.save(new Events(Long.valueOf(1))), "Podsjetnik za događaj 1", LocalDate.of(Integer.parseInt("2022"),Integer.parseInt("2"),Integer.parseInt("2")), false));
            repository.save(new Notification(repository3.save(new User(Long.valueOf(122))), repository2.save(new Events(Long.valueOf(1))),"Podsjetnik za događaj 2",LocalDate.of(Integer.parseInt("2022"),Integer.parseInt("2"),Integer.parseInt("2")), false));
            repository.save(new Notification(repository3.save(new User(Long.valueOf(123))),repository2.save(new Events(Long.valueOf(1))),"Podsjetnik za događaj 3", LocalDate.of(Integer.parseInt("2022"),Integer.parseInt("2"),Integer.parseInt("2")), false));

            log.info("Notifications found with findAll():");
            log.info("-------------------------------");

            for (Notification notification : repository.findAll()) {
                log.info(notification.toString());
            }
            log.info("");

            repository2.save(new Events(Long.valueOf(1)));
            repository2.save(new Events(Long.valueOf(2)));
            repository2.save(new Events(Long.valueOf(3)));

            log.info("Events found with findAll():");
            log.info("-------------------------------");

            for (Events event : repository2.findAll()) {
                log.info(event.toString());
            }
            log.info("");

            repository3.save(new User(Long.valueOf(12)));
            repository3.save(new User(Long.valueOf(23)));
            repository3.save(new User(Long.valueOf(123)));

            Events e1 = new Events(Long.valueOf(4));
            Events e2 = new Events(Long.valueOf(5));

            User u1 = new User(Long.valueOf(1));

            User u2 = new User(Long.valueOf(2));

            User u3 = new User(Long.valueOf(3));

            u1.getEvents().add(e1);
            u1.getEvents().add(e2);
            e1.getUsers().add(u1);
            e2.getUsers().add(u1);

            u1.getSubscriber().add(u2);
            u1.getSubscriber().add(u3);
            u2.getSubsribedTo().add(u1);
            u3.getSubsribedTo().add(u1);

            repository3.save(u1);
            repository3.save(u2);
            repository3.save(u3);


            log.info("Events found with findAll():");
            log.info("-------------------------------");

            for (User user : repository3.findAll()) {
                log.info(user.toString());
            }
            log.info("");
        };
    }


}