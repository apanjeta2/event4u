package com.event4u.eventsservice;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getAllUsers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[1].id", is(5)))
                .andExpect(jsonPath("$[2].id", is(6)))
        ;
    }

    @Test
    public void getUserById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/users/4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
        ;
    }

    @Test
    public Long getNumberOfUsers() throws Exception {
        MvcResult r = mvc.perform(MockMvcRequestBuilders.get("events-micro/users/count")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        return Long.valueOf(r.getResponse().getContentAsString());
    }

    @Order(1)
    @Test
    public void addUser() throws Exception {
        Long count1 = getNumberOfUsers();
        mvc.perform(MockMvcRequestBuilders.post("events-micro/users/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(10)));
        Long count2 = getNumberOfUsers();
        if (count1 != count2-1) {
            throw new Exception("Add new user test: User not added!");
        }
    }

    @Order(2)
    @Test
    public void deleteNewUser() throws Exception {
        Long count1 = getNumberOfUsers();
        mvc.perform(MockMvcRequestBuilders.delete("events-micro/users/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(10)));
        Long count2 = getNumberOfUsers();
        if (count1 != count2+1) {
            throw new Exception("Delete new user test: User not deleted!");
        }
    }
}
