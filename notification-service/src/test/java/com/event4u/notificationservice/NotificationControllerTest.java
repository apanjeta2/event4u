package com.event4u.notificationservice;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.event4u.notificationservice.controller.NotificationController;
import com.event4u.notificationservice.model.UserAll;
import com.event4u.notificationservice.service.UserService;
import net.minidev.json.JSONObject;
import org.apache.tomcat.util.json.JSONParser;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc

public class NotificationControllerTest {


    @Autowired
    private MockMvc mvc;
    @InjectMocks
    private NotificationController notificationController;

    @Autowired
    private ServiceInstanceRestController serviceInstanceRestController;

    @Autowired
    private UserService userService;

    @Test
    public void getAllNotificationsTest() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/notifications")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventId", is(1))) //Prva notifikacija je ona na poceku unesena, sa id-om1 i userom sa id-om 12
                .andExpect(jsonPath("$[0].userId", is(12)))
                .andExpect(jsonPath("$[0].isRead", is(false)));
    }
    @Test
    public void getAllNotificationsTestWrongMethod() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.put("/notifications")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("Request method 'PUT' not supported")));
    }
    @Test
    public void getNotificationByIdTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getById/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("notificationId", is(2)))
                .andExpect(jsonPath("eventId", is(1)))
                .andExpect(jsonPath("userId", is(122)))
        ;
    }

    @Test
    //Pogresan parametar error
    public void getNotificationByIdErrorBadParameterTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/notifications/getById/\"1\"")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"1\"\"")))
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
                .andExpect(jsonPath("$.message", is("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"1\"\"")))

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
                .andExpect(jsonPath("$.message", is("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"1\"\"")))
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
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].notificationId", is(2)))
                .andExpect(jsonPath("$[0].eventId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(122)))
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
                .andExpect(jsonPath("$.message", is("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"\"1\"\"")))
        ;

    }

    @Autowired
    private MockMvc mockMvc;

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
                .andExpect(jsonPath("$.message", is("Successful deletion of the notification with user id: 123")))
                .andExpect(status().isOk());
    }
    @Test
    public void deleteNotificationByUserIdErrorTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/notifications/deleteByUserId/11"))
                .andExpect(jsonPath("$.message", is("Error deleting notifications with user id: 11")))
                .andExpect(status().isBadRequest());
    }

    //Kreiranje nove notifikacije preko body

    public String token="";
    @Before
    public void setup() {
        String odg =  userService.getValidToken();
        int intIndex = odg.indexOf("token") +8;
        token=odg.substring(intIndex,350);
    }
    @Test
    public void postNotification2Test() throws Exception {
        setup();
        HttpHeaders httph = new HttpHeaders();
        httph.add("Content-Type","application/json");
        httph.add("Authorization", "Bearer "+token);//+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OCwibmFtZSI6ImFqbGEiLCJzdXJuYW1lIjoiemFwcmF0aXRlIG5hIGluc3RhZ3JhbXUiLCJ1c2VybmFtZSI6ImFqbGFwIiwiaWF0IjoxNTg2NTQ0MjY5LCJleHAiOjE1ODY1NDc4Njl9._VL0bXr3tLe5pG2wcC57OoGDrf6YgNavCoO4gSw90Ec");
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/notifications/postNotification")
                .content("{\n" +
                        "\t\"eventId\": 1,\n" +
                        "\t\"name\": \"Ime eventa\",\n" +
                        "\t\"date\": \"2020-07-07\"\n" +
                        "}").headers(httph))//.header("Authorization", "Bearer "+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId", is(6)))
                .andExpect(jsonPath("$.eventId", is(1)))
                .andExpect(jsonPath("$.userId", is(1)))
                .andReturn();
    }
    @Test
    public void postNotification2TestError() throws Exception {
        setup();
        HttpHeaders httph = new HttpHeaders();
        httph.add("Content-Type","application/json");
        httph.add("Authorization", "Bearer "+token);//+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OCwibmFtZSI6ImFqbGEiLCJzdXJuYW1lIjoiemFwcmF0aXRlIG5hIGluc3RhZ3JhbXUiLCJ1c2VybmFtZSI6ImFqbGFwIiwiaWF0IjoxNTg2NTQ0MjY5LCJleHAiOjE1ODY1NDc4Njl9._VL0bXr3tLe5pG2wcC57OoGDrf6YgNavCoO4gSw90Ec");
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/notifications/postNotification")
                .content("{\n" +
                        "\t\"eventId\": 17777,\n" +
                        "\t\"name\": \"Ime eventa\",\n" +
                        "\t\"date\": \"2020-07-07\"\n" +
                        "}").headers(httph))//.header("Authorization", "Bearer "+token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Element not found")))
                .andReturn();
    }

    @Test
    public void postNotificationForGoingToTest() throws Exception {
        setup();
        HttpHeaders httph = new HttpHeaders();
        httph.add("Content-Type","application/json");
        httph.add("Authorization", "Bearer "+token);//+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OCwibmFtZSI6ImFqbGEiLCJzdXJuYW1lIjoiemFwcmF0aXRlIG5hIGluc3RhZ3JhbXUiLCJ1c2VybmFtZSI6ImFqbGFwIiwiaWF0IjoxNTg2NTQ0MjY5LCJleHAiOjE1ODY1NDc4Njl9._VL0bXr3tLe5pG2wcC57OoGDrf6YgNavCoO4gSw90Ec");
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/notifications/createGoingTo/2")
                .content("").headers(httph))//.header("Authorization", "Bearer "+token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId", is(2)))
                .andReturn();
    }
    @Test
    public void postNotificationForGoingToTestError() throws Exception {
        setup();
        HttpHeaders httph = new HttpHeaders();
        httph.add("Content-Type","application/json");
        httph.add("Authorization", "Bearer "+token);//+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OCwibmFtZSI6ImFqbGEiLCJzdXJuYW1lIjoiemFwcmF0aXRlIG5hIGluc3RhZ3JhbXUiLCJ1c2VybmFtZSI6ImFqbGFwIiwiaWF0IjoxNTg2NTQ0MjY5LCJleHAiOjE1ODY1NDc4Njl9._VL0bXr3tLe5pG2wcC57OoGDrf6YgNavCoO4gSw90Ec");
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.post("/notifications/createGoingTo/662")
                .content("").headers(httph))//.header("Authorization", "Bearer "+token))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Element not found")))
                .andReturn();
    }
    @Test
    public void updateNotificationTest() throws Exception {
        setup();
        HttpHeaders httph = new HttpHeaders();
        httph.add("Content-Type","application/json");
        httph.add("Authorization", "Bearer "+token);//+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OCwibmFtZSI6ImFqbGEiLCJzdXJuYW1lIjoiemFwcmF0aXRlIG5hIGluc3RhZ3JhbXUiLCJ1c2VybmFtZSI6ImFqbGFwIiwiaWF0IjoxNTg2NTQ0MjY5LCJleHAiOjE1ODY1NDc4Njl9._VL0bXr3tLe5pG2wcC57OoGDrf6YgNavCoO4gSw90Ec");
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.put("/notifications/2")
                .content("{\n" +
                        "\t\"eventId\": 663,\n" +
                        "\t\"name\": \"Ime eventa\",\n" +
                        "\t\"date\": \"2020-07-07\"\n" +
                        "}").headers(httph))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Element not found")))
                .andReturn();
    }
    @Test
    public void updateNotificationTestError() throws Exception {
        setup();
        HttpHeaders httph = new HttpHeaders();
        httph.add("Content-Type","application/json");
        httph.add("Authorization", "Bearer "+token);//+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6OCwibmFtZSI6ImFqbGEiLCJzdXJuYW1lIjoiemFwcmF0aXRlIG5hIGluc3RhZ3JhbXUiLCJ1c2VybmFtZSI6ImFqbGFwIiwiaWF0IjoxNTg2NTQ0MjY5LCJleHAiOjE1ODY1NDc4Njl9._VL0bXr3tLe5pG2wcC57OoGDrf6YgNavCoO4gSw90Ec");
        MvcResult rez = mvc.perform(MockMvcRequestBuilders.put("/notifications/2")
                .content("{\n" +
                        "\t\"eventId\": 355,\n" +
                        "\t\"name\": \"Ime eventa\",\n" +
                        "\t\"date\": \"2020-07-07\"\n" +
                        "}").headers(httph))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Element not found")))
                .andReturn();
    }
}
