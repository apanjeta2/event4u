package com.event4u.eventsservice;
import com.event4u.eventsservice.model.*;
import com.event4u.eventsservice.model.Event;
import com.event4u.eventsservice.repository.*;
import com.event4u.eventsservice.service.EventService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import java.awt.*;
import java.time.LocalDate;
@EnableEurekaClient
@SpringBootApplication
public class EventsServiceApplication {
	static final String queueName = "events-notifications";
	public enum Status {CREATE_STARTED, CREATE_FINISHED, CREATE_FAILED, CREATE_FAILED_AGAIN};
	@Autowired
	Sender sender;
	private static final Logger log =
			LoggerFactory.getLogger(EventsServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(EventsServiceApplication.class, args);
	}
	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}
	@Bean
	Queue queueNE() {return new Queue("notification-events", false); }
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
											 MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames("notifications-events");
		container.setMessageListener(listenerAdapter);
		return container;
	}
	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "listen");
	}
	@Bean
	TopicExchange exchange()
	{
		return new TopicExchange("message_queue_exchange");
	}
	@Bean
	Binding binding(Queue queue, TopicExchange exchange)
	{
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

	@Autowired
	EventService eventService;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Bean
	public CommandLineRunner demo(CategoryRepository cRepository, UserRepository uRepository, LocationRepository lRepository, EventRepository eRepository, EventUserRepository euRepository) {
		return (args) -> {
			Category c1 = cRepository.save(new Category("Outdoors&Adventure"));
			cRepository.save(new Category("Tech"));
			cRepository.save(new Category("Learning"));
			log.info("Categories found with findAll():");
			log.info("-------------------------------");
			for (Category c : cRepository.findAll()) {
				log.info(c.toString());
			}
			log.info("");
			Long id = Long.valueOf(1);
			User u1 = uRepository.save(new User(id));
			User u2 = uRepository.save(new User(++id));
			uRepository.save(new User(++id));
			log.info("Users found with findAll():");
			log.info("-------------------------------");
			for (User u: uRepository.findAll()) {
				log.info(u.toString());
			}
			log.info("");
			Location l1 = lRepository.save(new Location(new Point(0,0), "Sarajevo", "Bosna i Hercegovina"));
			lRepository.save(new Location(new Point(0,0), "Mostar", "Bosna i Hercegovina"));
			lRepository.save(new Location(new Point(0,0), "Banja Luka", "Bosna i Hercegovina"));
			log.info("Locations found with findAll():");
			log.info("-------------------------------");
			for (Location l: lRepository.findAll()) {
				log.info(l.toString());
			}
			log.info("");
			Event e1 = eRepository.save(new Event("LV4 NWT",
					"Zmaja od Bosne bb",
					LocalDate.of(2020, 3, 23),
					"Laboratorijske vježbe iz predmeta NWT",
					Boolean.TRUE,
					c1,
					u1,
					l1
			));
			eventService.changeStatus(e1.getId(),Status.CREATE_FINISHED);
			Event e2 = eRepository.save(
					new Event("LV4 NWT",
							"Zmaja od Bosne bb",
							LocalDate.of(2020, 3, 30),
							"Laboratorijske vježbe iz predmeta NWT",
							Boolean.TRUE,
							c1,
							u1,
							l1
					));
			eventService.changeStatus(e2.getId(),Status.CREATE_FINISHED);
			log.info("Events found with findAll():");
			log.info("-------------------------------");
			for (Event e: eRepository.findAll()) {
				log.info(e.toString());
			}
			log.info("");
			euRepository.save(new EventUser(u2, e1, Boolean.TRUE));
			log.info("Event-User found with findAll():");
			log.info("-------------------------------");
			for (EventUser eu: euRepository.findAll()) {
				log.info(eu.toString());
			}
			log.info("");
			/*String command =
					"curl -i -H \"Content-Type: application/json\" -d '{\"title\": \"LV5 NWT\",\"address\": \"Zmaja od Bosne bb\",\"date\": \"2020-03-21\",\"description\": \"Laboratorijske vježbe iz predmeta NWT\",\"idCategory\": 1,\"idUser\": 5,\"idLocation\": 7}' http://localhost:8080/events-micro/events";
			ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));*/
		};
	}
}