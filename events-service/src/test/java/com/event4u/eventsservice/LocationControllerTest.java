package com.event4u.eventsservice;

import com.event4u.eventsservice.model.Location;

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

import java.awt.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    public void getAllLocations() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/locations")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(7)))
                .andExpect(jsonPath("$[0].city", is("Sarajevo")))
                .andExpect(jsonPath("$[0].country", is("Bosna i Hercegovina")))
                .andExpect(jsonPath("$[1].id", is(8)))
                .andExpect(jsonPath("$[1].city", is("Mostar")))
                .andExpect(jsonPath("$[1].country", is("Bosna i Hercegovina")))
                .andExpect(jsonPath("$[2].id", is(9)))
                .andExpect(jsonPath("$[2].city", is("Banja Luka")))
                .andExpect(jsonPath("$[2].country", is("Bosna i Hercegovina")))
        ;
    }

    @Test
    @Order(2)
    public void getLocationById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/locations/7")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(7)))
                .andExpect(jsonPath("$.city", is("Sarajevo")))
                .andExpect(jsonPath("$.country", is("Bosna i Hercegovina")))
        ;
    }

    @Test
    @Order(3)
    public void getNumberOfLocations() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/locations/count")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("3"))
        ;
    }

    String res;
    Location newLocation;

    public void convertResToLocation() {
        ObjectMapper m = new ObjectMapper();

        try {
            newLocation = m.readValue(res, Location.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public String convertLocationToJson() {
        ObjectMapper m = new ObjectMapper();
        try {
            return m.writeValueAsString(new Location(new Point(0,0), "Travnik", "Bosna i Hercegovina"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Test
    @Order(4)
    public void createLocation() throws Exception {
        MvcResult r = mvc.perform(MockMvcRequestBuilders.post("/events-micro/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertLocationToJson()))
                .andExpect(status().isOk())
                .andReturn()
                ;
        res = r.getResponse().getContentAsString();
        convertResToLocation();
    }

    @Test
    @Order(5)
    public void updateCategoryById() throws Exception {
        createLocation();
        newLocation.setCountry("BiH");
        String url = "/events-micro/locations/"+newLocation.getId().toString();
        MvcResult r = mvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertLocationToJson()))
                .andExpect(status().isOk())
                .andReturn()
                ;
        res = r.getResponse().getContentAsString();
        convertResToLocation();
    }

    @Test
    @Order(6)
    public void deleteCategoryById() throws Exception {
        createLocation();
        String url = "/events-micro/locations/"+newLocation.getId().toString();
        mvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(status().isOk());
    }

}


