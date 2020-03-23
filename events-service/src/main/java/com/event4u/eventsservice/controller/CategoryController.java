package com.event4u.eventsservice.controller;

import com.event4u.eventsservice.model.Category;
import com.event4u.eventsservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("events-micro/categories")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //Get all categories
    @GetMapping("")
    public List<Category> allCategories() {
        return categoryService.findAll();
    }

    //Get number of categories
    @GetMapping("/count")
    public Long count() {
        return categoryService.count();
    }

    //Get category by id
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    //Add new category
    @PostMapping("")
    public Category newCategory(@RequestBody String name) {
        return categoryService.createCategory(name);
    }

    //Delete category by id
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        Long userId = Long.parseLong(id);
        categoryService.deleteById(userId);
    }

    //Update category
    @PutMapping("/{id}")
    public Category updateCategory(@RequestBody String name, @PathVariable Long id) {
        return categoryService.updateCategory(id, name);
    }

}
