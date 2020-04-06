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
    public void getAllNotificationsTest() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/notifications")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(12)))
                .andExpect(jsonPath("$[0].isRead", is(false)));
    }
    @Test
    public void getNotificationByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getById/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId", is(2)))
                .andExpect(jsonPath("$.eventId", is(1)))
                .andExpect(jsonPath("$.userId", is(122)))
                .andExpect(jsonPath("$.isRead", is(false)))
        ;
    }

    @Test
    //Pogresan parametar error
    public void getNotificationByIdErrorBadParameterTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getById/\"1\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
}

    @Test
//Ako ne postoji u bazi
    public void getNotificationByIdErrorNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getById/25")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Element not found")))
        ;
    }

    @Test
    public void getNotificationByUserIdNotReadTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserIdNotRead/122")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].notificationId", is(2)))
                .andExpect(jsonPath("$[0].eventId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(122)))
        ;
    }

    @Test
    //Pogresan parametar error
    public void getNotificationByIdNotReadErrorBadParameterTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserIdNotRead/\"1\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    //Ako ne postoji u bazi
    public void getNotificationByIdNotReadErrorNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserIdNotRead/25")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Element not found")))
        ;
    }
    @Test
    public void getNotificationByUserIdReadEmptyTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserIdRead/23")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(status().isOk())
        ;
    }

    @Test
    //Pogresan parametar error
    public void getNotificationByIdReadErrorBadParameterTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserIdRead/\"1\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    //Ako ne postoji u bazi
    public void getNotificationByIdReadErrorNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserIdRead/25")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Element not found")))
        ;
    }


    @Test
    public void getNotificationByUserIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserId/122")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].notificationId", is(2)))
                .andExpect(jsonPath("$[0].eventId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(122)))
        ;
    }

    @Test
    //Pogresan parametar error
    public void getNotificationByUserIdErrorBadParameterTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserId/\"1\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    //Ako ne postoji u bazi
    public void getNotificationByUserIdErrorNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserId/25")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Element not found")))
        ;
    }
    @Test
    public void getNotificationByEventIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByEventId/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].notificationId", is(2)))
                .andExpect(jsonPath("$[0].eventId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(122)))
        ;
    }
    @Test
    public void getNotificationByEventIdErrorTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByEventId/91")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
        ;
    }
    @Test
    public void getNotificationByUserIdErrorTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserId/18888888")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
        ;
    }
    @Test
    public void getNotificationByEventIdNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByUserId/1222")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Element not found")))
        ;
    }
    @Test
    public void getNotificationByEventIdErrorBadParameterTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getByEventId/\"1\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    public void postNotificationTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/notifications?userId=12&eventId=2&message=\"poruka test\"&date=22/06/2020&isRead=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId", is(4)))
                .andExpect(jsonPath("$.eventId", is(2)))
                .andExpect(jsonPath("$.userId", is(12)))
                .andReturn();
    }
    @Test
    public void postNotificationBadParameterTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/notifications?userId=\"12\"&eventId=2&message=\"poruka test\"&date=22/06/2020&isRead=false"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    @Test
    public void postNotificationMissingParameterTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/notifications?eventId=2&message=\"poruka test\"&date=22/06/2020&isRead=false"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Required Long parameter 'userId' is not present")))
                .andReturn();
    }
    @Test
    public void postNotificationBadDateFormatTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/notifications?userId=12&eventId=2&message=\"poruka test\"&date=tt222.06.2020&isRead=false"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.errors[0]", is("Error occurred")))
                .andReturn();
    }
    @Test
    public void deleteNotificationByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/notifications/1"))
                .andExpect(jsonPath("$.message", is("Successful deletion of the notification with id: 1")))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteNotificationByIdErrorTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/notifications/11"))
                .andExpect(jsonPath("$.message", is("Error deleting notifications with id: 11")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteNotificationByUserIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/notifications/deleteByUserId/123"))
                .andExpect(jsonPath("$.message", is("Successful deletion of the notification with id: 123")))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteNotificationByUserIdErrorTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/notifications/deleteByUserId/11"))
                .andExpect(jsonPath("$.message", is("Error deleting notifications with id: 11")))
                .andExpect(status().isBadRequest());
    }
}
