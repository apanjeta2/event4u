package com.event4u.eventsservice;

import com.event4u.eventsservice.model.Category;
import com.event4u.eventsservice.repository.CategoryInterface;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class EventsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsServiceApplication.class, args);
	}

//	@Bean
//	ApplicationRunner init(CategoryInterface repoCat) {
//		List<String> categories = Arrays.asList(new String[]{
//				"movies", "books", "programming"
//		});
//		return args -> {
//			try {
//				for (String s: categories
//					 ) {
//					repoCat.save(new Category());
//				}
//			}
//			catch (Exception e) {
//				e.printStackTrace();;
//			}
//			repoCat.findAll().forEach(System.out::println);
//		};
//	}

}
