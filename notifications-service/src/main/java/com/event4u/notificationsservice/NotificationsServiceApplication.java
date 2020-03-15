package com.event4u.notificationsservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotificationsServiceApplication {

    private static final Logger log = 
            LoggerFactory.getLogger(NotificationsServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NotificationsServiceApplication.class, args);
    }

/*
    @Bean
    public CommandLineRunner demo(NotificationRepository repository) {
        return (args) -> {
            // save a few customers
            repository.save(new Notification("Jack"));
            
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Notification notification : repository.findAll()) {
                log.info(notification.toString());
            }
            log.info("");
        };
    }*/
}