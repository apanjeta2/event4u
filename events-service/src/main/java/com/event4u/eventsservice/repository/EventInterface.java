package com.event4u.eventsservice.repository;

import com.event4u.eventsservice.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventInterface extends CrudRepository<Event, Long>  {
}
