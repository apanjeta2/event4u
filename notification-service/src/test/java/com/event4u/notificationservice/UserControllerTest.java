package com.event4u.notificationservice;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.event4u.notificationservice.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
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
    @Autowired
    private UserService userService;

    @org.junit.jupiter.api.Test
    public void getAllUsersTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId", Matchers.is(1)));
    }
    @org.junit.jupiter.api.Test
    public void getUserByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/getById/12")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(12)));
    }
    @Test
    public void getUserByIdErrorTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/getById/88") //ne postoji
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Element not found")));
    }
    @Test
    public void postUserByIdTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/users")
                .content("66"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(66)))
                .andReturn();
    }
    @Test
    public void postUserByIdErrorTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/users")
                .content(""))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void updateUserTest() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.put("/events/2").header("Content-Type", "application/json")
                .content("{\"eventId\": 2,\"name\": \"promjena\",\"date\": \"2020-09-07\" }"))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId", is(2))) //ako je uspjeno upisan ispise ga
                .andReturn();
    }

    @Test
    public void postUserByIdTest2() throws Exception {
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/users")
                .content("666"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(666)))
                .andReturn();
    }
    //Istog usera kojeg smo dodali brisemo, sto znaci da je uspjesno dodan
    @Test
    public void deleteUserTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/666"))
                .andExpect(jsonPath("$.message", is("Successful deletion of the user with id: 666")))
                .andExpect(status().isOk());
    }
    public String token="";

    @Before
    public void setup() {
        String odg =  userService.getValidToken();
        int intIndex = odg.indexOf("token") +8;
        token=odg.substring(intIndex,350);
    }
    @Test
    public void subscribeToTest() throws Exception {
        setup();
        HttpHeaders httph = new HttpHeaders();
        httph.add("Content-Type","application/json");
        httph.add("Authorization", "Bearer "+token);
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/users/subscribeTo")
                .content("1").headers(httph))  //U bazi user managmenta je samo user sa id-om 1 pa njega dodajemo u listu subscriber-a
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Subscribed to user with id 1")))
                .andReturn();
    }
    @Test
    public void subscribeToTestError() throws Exception {
        setup();
        HttpHeaders httph = new HttpHeaders();
        httph.add("Content-Type","application/json");
        httph.add("Authorization", "Bearer "+token);
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/users/subscribeTo")
                .content("z").headers(httph))  //U bazi user managmenta je samo user sa id-om 1 pa njega dodajemo u listu subscriber-a
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message", is("For input string: \"z\"")))
                .andReturn();
    }
    @Test
    public void getFullSubscribersTest() throws Exception {
        setup();
        HttpHeaders httph = new HttpHeaders();
        httph.add("Content-Type","application/json");
        httph.add("Authorization", "Bearer "+token);
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.get("/users/getFullSubscribers")
                .content("").headers(httph))  //U bazi user managmenta je samo user sa id-om 1 pa njega dodajemo u listu subscriber-a
                .andExpect(status().isOk())
                //AKo je uspjesno dodan susbscriber u prethodnom testu ovaj test će pravilno pokupiti iz baze sa usermanagment servisa
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].username", is("mashashama")))
                .andExpect(jsonPath("$[0].name", is("mashonista")))

                .andReturn();
    }
}
