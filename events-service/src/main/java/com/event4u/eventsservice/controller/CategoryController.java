package com.event4u.eventsservice.controller;


import com.event4u.eventsservice.repository.CategoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("category")
@RestController
public class CategoryController {
    @Autowired
    private CategoryInterface categoryRepository;

}
