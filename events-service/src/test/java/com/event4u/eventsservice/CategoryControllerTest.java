package com.event4u.eventsservice;

import com.event4u.eventsservice.model.Category;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllCategories() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/categories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("movies")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("books")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("IT")))
                ;
    }

    @Test
    public void getCategoryById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/categories/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("movies")))
        ;
    }

    @Test
    public void getNumberOfCategories() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/categories/count")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("3"))
        ;
    }

    String res;
    Category newCategory;

    public void convertResToCategory() {
        ObjectMapper m = new ObjectMapper();

        try {
            newCategory = m.readValue(res, Category.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createCategory() throws Exception {
        MvcResult r = mvc.perform(MockMvcRequestBuilders.post("/events-micro/categories")
                .content("Sports"))
                .andExpect(status().isOk())
                .andReturn()
        ;
        res = r.getResponse().getContentAsString();
        convertResToCategory();
    }

    @Test
    public void updateCategoryById() throws Exception {
        createCategory();
        String url = "/events-micro/categories/"+newCategory.getId().toString();
        MvcResult r = mvc.perform(MockMvcRequestBuilders.put(url)
                .content("NWT"))
                .andExpect(status().isOk())
                .andReturn()
                ;
        res = r.getResponse().getContentAsString();
        convertResToCategory();
    }

    @Test
    public void deleteCategoryById() throws Exception {
        createCategory();
        String url = "/events-micro/categories/"+newCategory.getId().toString();
        mvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(status().isOk());
    }


}
