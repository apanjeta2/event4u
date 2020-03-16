package com.event4u.eventsservice.repository;

import com.event4u.eventsservice.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryInterface extends CrudRepository<Category, Long> {
}
