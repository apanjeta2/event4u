package com.event4u.eventsservice;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class EventUserController {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getGoingForEvent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/going/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
        ;
    }

    @Test
    public void getInterestedForEvent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/interested/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
        ;
    }

    @Test
    public void getNumberOfGoingForEvent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/goingNum/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("1"))
        ;
    }

    @Test
    public void getNumberOfInterestedForEvent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/interestedNum/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("0"))
        ;
    }


}

