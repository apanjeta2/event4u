package com.event4u.eventsservice.repository;

import com.event4u.eventsservice.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationInterface extends CrudRepository<Location, Long> {
}
