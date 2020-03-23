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
                .andExpect(jsonPath("$", hasSize(4))) //4 zbogo novog dodanog
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
    public void postEventByIdTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/events")
                .content("66"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
