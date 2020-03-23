package com.event4u.eventsservice.repository;

import com.event4u.eventsservice.model.EventUser;
import org.springframework.data.repository.CrudRepository;

public interface EventUserRepository extends CrudRepository<EventUser, Long>  {
}
