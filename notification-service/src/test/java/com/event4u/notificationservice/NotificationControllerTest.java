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

public class NotificationControllerTest {


    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllNotificationsAPI() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/notifications")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(122)))
                .andExpect(jsonPath("$[0].isRead", is(false)))
                .andExpect(jsonPath("$[1].eventId", is(1)))
                .andExpect(jsonPath("$[1].userId", is(123)))
                .andExpect(jsonPath("$[1].isRead", is(false)));
    }
    @Test
    public void getNotificationByIdAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getById/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId", is(1)))
                .andExpect(jsonPath("$.eventId", is(1)))
        ;
    }
    @Test
    public void getNotificationByUserIdReadAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserIdRead/12")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
        ;
    }
    @Test
    public void getNotificationByUserIdNotReadAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserIdNotRead/122")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
        ;
    }
    @Test
    public void getNotificationByEventIdErrorAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByEventId/91")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
        ;
    }
    @Test
    public void getNotificationByUserIdErrorAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserId/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
        ;
    }
    @Test
    public void getNotificationByUserIdAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserId/122")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].notificationId", is(2)))
                .andExpect(jsonPath("$[0].eventId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(122)))
        ;
    }
    @Test
    public void postNotificationTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/notifications?userId=12&eventId=2&message=\"poruka test\"&date=22/06/2020&isRead=false"))
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    public void deleteNotificationByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/notifications/1"))
                .andExpect(status().isOk());
    }
}
