package com.event4u.notificationservice;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllEventsTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].eventId", is(1)));

    }
    @Test
    public void getEventByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events/getById/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId", is(2)));
    }

    @Test
    public void getEventByIdErrorTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events/getById/88") //ne postoji
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Element not found")));
    }
    @Test
    public void postEventByIdTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/events")
                .content("66"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId", is(66))) //ako je uspjeno upisan ispise ga
                .andReturn();
    }
    @Test
    public void postEventByIdErrorTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/events")
                .content(""))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void updateEventTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.put("/events/2").header("Content-Type", "application/json")
                .content("{\"eventId\": 2,\"name\": \"promjena\",\"date\": \"2020-09-07\" }"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId", is(2))) //ako je uspjeno upisan ispise ga
                .andReturn();
    }

    @Test
    public void postEventByIdTest2() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/events")
                .content("666"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId", is(666))) //ako je uspjeno upisan ispise ga
                .andReturn();
    }
    @Test
    public void deleteEventTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/events/666"))
                .andExpect(jsonPath("$.message", is("Successful deletion of the event with id: 666")))
                        .andExpect(status().isOk());
    }
}
