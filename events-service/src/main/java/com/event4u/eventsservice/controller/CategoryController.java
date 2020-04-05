package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.model.Category;
import com.event4u.eventsservice.model.SuccessMessage;
import com.event4u.eventsservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("events-micro/categories")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //Get all categories
    @GetMapping(path ="", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Category> allCategories() {
        return categoryService.findAll();
    }

    //Get number of categories
    @GetMapping(path = "/count", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Long count() {
        return categoryService.count();
    }

    //Get category by id
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    //Add new category
    @PostMapping(path = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Category newCategory(@RequestBody String name) {
        return categoryService.createCategory(name);
    }

    //Delete category by id
    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessMessage deleteCategory(@PathVariable String id) {
        Long categoryId = Long.parseLong(id);
        categoryService.deleteById(categoryId);
        return new SuccessMessage("Category with id " +  id + " successfully deleted");
    }

    //Update category
    @PutMapping(path ="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Category updateCategory(@RequestBody String name, @PathVariable Long id) {
        return categoryService.updateCategory(id, name);
    }

}
