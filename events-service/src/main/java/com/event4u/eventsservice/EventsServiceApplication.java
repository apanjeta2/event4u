package com.event4u.eventsservice;

import com.event4u.eventsservice.model.*;
import com.event4u.eventsservice.model.Event;
import com.event4u.eventsservice.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.awt.*;
import java.util.Date;


@SpringBootApplication
public class EventsServiceApplication {
	private static final Logger log =
			LoggerFactory.getLogger(EventsServiceApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(EventsServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CategoryInterface cRepository, UserInterface uRepository, LocationInterface lRepository, EventInterface eRepository, EventUserInterface euRepository) {
		return (args) -> {
			// save a few categories
			cRepository.save(new Category("movies", null));
			cRepository.save(new Category("books", null));
			cRepository.save(new Category("IT", null));

			log.info("Categories found with findAll():");
			log.info("-------------------------------");

			for (Category c : cRepository.findAll()) {
				log.info(c.toString());
			}
			log.info("");

			// save a few users
			User u1 = uRepository.save(new User());
			uRepository.save(new User());
			uRepository.save(new User());

			log.info("Users found with findAll():");
			log.info("-------------------------------");

			for (User u: uRepository.findAll()) {
				log.info(u.toString());
			}
			log.info("");

			// save a few locations
			lRepository.save(new Location(new Point(0,0), "Sarajevo", "Bosna i Hercegovina"));
			lRepository.save(new Location(new Point(0,0), "Mostar", "Bosna i Hercegovina"));
			lRepository.save(new Location(new Point(0,0), "Banja Luka", "Bosna i Hercegovina"));

			log.info("Locations found with findAll():");
			log.info("-------------------------------");

			for (Location l: lRepository.findAll()) {
				log.info(l.toString());
			}
			log.info("");

			// save a few events
			Event e1 = eRepository.save(new Event("LV3 NWT", "Zmaja od Bosne bb", new Date(2020,3,16), "Laboratorijske vježbe iz predmeta NWT", Boolean.TRUE));

			log.info("Events found with findAll():");
			log.info("-------------------------------");

			for (Event e: eRepository.findAll()) {
				log.info(e.toString());
			}
			log.info("");

			// mark a few events
			euRepository.save(new EventUser(u1, e1, Boolean.TRUE));

			log.info("Event-User found with findAll():");
			log.info("-------------------------------");

			for (EventUser eu: euRepository.findAll()) {
				log.info(eu.toString());
			}
			log.info("");
		};
	}

}
