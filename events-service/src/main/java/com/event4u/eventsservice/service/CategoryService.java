package com.event4u.eventsservice.service;

import com.event4u.eventsservice.model.Category;
import com.event4u.eventsservice.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    //TODO: ispraviti tako da nije rekurzija
    public List<Category> findAll() {
        var it = categoryRepository.findAll();

        var categories = new ArrayList<Category>();
        it.forEach(c -> {
            //Bez liste dogadjaja
            c.getEvents().forEach(e -> {
                e.setCategory(null);
            });
            categories.add(c);
        });
        return categories;
    }

    public Category findById(Long id) {
        //TODO: error handling
        return categoryRepository.findById(id).orElseThrow();

    }

    public Long count() { //Number of categories
        return categoryRepository.count();
    }

    public void deleteById(Long categoryId) {

        categoryRepository.deleteById(categoryId);
    }

    public Category createCategory(String name) {
        //TODO: Check if exists with same name
        return categoryRepository.save(new Category(name));
    }

    public Category updateCategory(Long id, String name) {
        //TODO: recursion problem again
          Category c = categoryRepository.findById(id).map(category -> {
            category.setName(name);
            return categoryRepository.save(category);
        }).orElseThrow();
        c.getEvents().forEach(e-> e.setCategory(null));
        return c;
    }

    //TODO add event to category API
}
