package com.event4u.notificationservice;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotificationServiceApplication {

    private static final Logger log = 
            LoggerFactory.getLogger(NotificationServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(NotificationRepository repository, EventsRepository repository2, UserRepository repository3) {
        return (args) -> {
            // save a few customers
            repository.save(new Notification(repository3.save(new User(Long.valueOf(12))), "Podsjetnik za događaj 1", new Date(), true));
            repository.save(new Notification(repository3.save(new User(Long.valueOf(122))),"Podsjetnik za događaj 2", new Date(), true));
            repository.save(new Notification(repository3.save(new User(Long.valueOf(123))),"Podsjetnik za događaj 3", new Date(), true));

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

            log.info("Events found with findAll():");
            log.info("-------------------------------");

            for (User user : repository3.findAll()) {
                log.info(user.toString());
            }
            log.info("");
        };
    }


}