package com.event4u.notificationservice;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.event4u.notificationservice.controller.NotificationController;
import com.event4u.notificationservice.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {


    @Autowired
    private MockMvc mvc;
    @MockBean
    private NotificationService service;

    @Autowired
    NotificationController notificationController;

    @Test
    public void whenNotificationController_thenNotNull() throws Exception {
        assertThat(notificationController).isNull();
    }

    @Test
    public void getAllNotificationsAPI() throws Exception
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
    public void getNotificationByIdAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId", is(1)))
                .andExpect(jsonPath("$.eventId", is(1)))
        ;
    }
}
