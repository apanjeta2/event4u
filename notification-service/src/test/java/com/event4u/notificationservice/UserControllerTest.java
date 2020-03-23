package com.event4u.notificationservice;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @org.junit.jupiter.api.Test
    public void getAllUsersTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4))) //4 zbog novog dodanog
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId", Matchers.is(12)));
    }
    @org.junit.jupiter.api.Test
    public void getUserByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/getById/12")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(12)));
    }

    @Test
    public void postEventByIdTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/users")
                .content("66"))
                .andExpect(status().isOk())
                .andReturn();
    }
}
