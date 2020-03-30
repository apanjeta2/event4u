package com.event4u.eventsservice;

import com.event4u.eventsservice.model.Category;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @Order(1)
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
    public void getCategoryByIdBadParameter() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/categories/-11")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", is("No value present")))
        ;
    }

    @Test
    public void getCategoryByIdBadParameterType() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/categories/fkdsj")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Order(2)
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

    @Order(3)
    @Test
    public void createCategory() throws Exception {
        MvcResult r = mvc.perform(MockMvcRequestBuilders.post("/events-micro/categories")
                .content("Fun"))
                .andExpect(status().isOk())
                .andReturn()
                ;
        res = r.getResponse().getContentAsString();
        convertResToCategory();
    }

    @Order(4)
    @Test
    public void createCategoryAlreadyExists() throws Exception {
        MvcResult r = mvc.perform(MockMvcRequestBuilders.post("/events-micro/categories")
                .content("Fun"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message",is("Category with name Fun already exists!")))
                .andReturn()
                ;
        res = r.getResponse().getContentAsString();
        convertResToCategory();
    }

    @Test
    public void createCategoryNoBody() throws Exception {
        MvcResult r = mvc.perform(MockMvcRequestBuilders.post("/events-micro/categories"))
                .andExpect(status().isBadRequest())
                .andReturn()
                ;
    }

    public void createCategoryHelper(String content) throws Exception {
        MvcResult r = mvc.perform(MockMvcRequestBuilders.post("/events-micro/categories")
                .content(content))
                .andExpect(status().isOk())
                .andReturn()
                ;
        res = r.getResponse().getContentAsString();
        convertResToCategory();
    }

    @Order(5)
    @Test
    public void updateCategoryById() throws Exception {
        createCategoryHelper("Sports");
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
    public void updateCategoryByIdBadRequest() throws Exception {
        createCategoryHelper("Cooking");
        String url = "/events-micro/categories/lkdsfjsldjf";
        MvcResult r = mvc.perform(MockMvcRequestBuilders.put(url)
                .content("NWT"))
                .andExpect(status().isBadRequest())
                .andReturn()
                ;
    }

    @Test
    public void deleteCategoryById() throws Exception {
        createCategoryHelper("Cooking");
        String url = "/events-micro/categories/"+newCategory.getId().toString();
        mvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(status().isOk());
    }

    @Test
    public void deleteCategoryByIdBadRequest() throws Exception {
        createCategoryHelper("Running");
        String url = "/events-micro/categories/sdf";
        mvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(status().is5xxServerError());
    }
}
