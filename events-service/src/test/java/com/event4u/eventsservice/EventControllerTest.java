package com.event4u.eventsservice;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.event4u.eventsservice.helpers.EventIdDeserializer;
import com.event4u.eventsservice.helpers.NewEventSerializer;
import com.event4u.eventsservice.model.EventDes;
import com.event4u.eventsservice.model.NewEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {
    @Autowired
    private MockMvc mvc;

    @Order(1)
    @Test
    public void getNumberOfEvents() throws Exception {
        MvcResult r = mvc.perform(MockMvcRequestBuilders.get("/events-micro/events/count")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("2"))
                .andReturn()
        ;
    }

    @Order(2)
    @Test
    public void getAllEvents() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/events")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(10)))
                .andExpect(jsonPath("$[0].address", is("Zmaja od Bosne bb")))
                .andExpect(jsonPath("$[0].date", is("2020-03-23")))
                .andExpect(jsonPath("$[0].description", is("Laboratorijske vježbe iz predmeta NWT")))
                .andExpect(jsonPath("$[0].category.id", is(1)))
                .andExpect(jsonPath("$[0].creator.id", is(4)))
                .andExpect(jsonPath("$[0].location.id", is(7)))
                .andExpect(jsonPath("$[0].title", is("LV4 NWT")))
                .andExpect(jsonPath("$[0].active", is(true)));

    }

    @Order(3)
    @Test
    public void getEventById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/events-micro/events/10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.address", is("Zmaja od Bosne bb")))
                .andExpect(jsonPath("$.date", is("2020-03-23")))
                .andExpect(jsonPath("$.description", is("Laboratorijske vježbe iz predmeta NWT")))
                .andExpect(jsonPath("$.category.id", is(1)))
                .andExpect(jsonPath("$.creator.id", is(4)))
                .andExpect(jsonPath("$.location.id", is(7)))
                .andExpect(jsonPath("$.title", is("LV4 NWT")))
                .andExpect(jsonPath("$.active", is(true)));

        ;
    }

    String res;

    public int getEventsCount() throws Exception {
        MvcResult r = mvc.perform(MockMvcRequestBuilders.get("/events-micro/events/count")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                ;
        String num = r.getResponse().getContentAsString();
        return Integer.parseInt(num);
    }

    /*public Event convertResToEvent() {
        ObjectMapper m = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        //module.addDeserializer(Event.class, new EventDeserializer());
        m.registerModule(module);
        try {
            return m.readValue(res, Event.class);
        }
         catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public EventDes getEventFromRes() {
        ObjectMapper m = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(EventDes.class, new EventIdDeserializer());
        m.registerModule(module);
        try {
            return m.readValue(res, EventDes.class);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String convertEventToJson(NewEvent event) {
        ObjectMapper m = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(NewEvent.class, new NewEventSerializer(NewEvent.class));
        m.registerModule(module);
        try {
            String s =  m.writeValueAsString(event);
            return s;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Order(4)
    @Test
    public EventDes createEvent() throws Exception {
        int prev = getEventsCount();
        NewEvent newEvent = new NewEvent("LV6 NWT", "Zmaja od Bosne bb", LocalDate.of(2020,03,30), "Laboratorijske vježbe iz predmeta NWT", new Long(1), new Long(6), new Long(7), Boolean.TRUE );
        MvcResult r = mvc.perform(MockMvcRequestBuilders.post("/events-micro/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertEventToJson(newEvent)))
                .andExpect(status().isOk())
                .andReturn()
                ;
        res = r.getResponse().getContentAsString();
        EventDes createdEvent = getEventFromRes();
        if ((prev+1) != getEventsCount()) throw new AssertionError("Event not created");
        return createdEvent;
    }

    @Order(5)
    @Test
    public void updateEventById() throws Exception {
        EventDes event = createEvent();
        NewEvent newEvent = new NewEvent("LV7 NWT", "Zmaja od Bosne bb", LocalDate.of(2020,04,07), "Laboratorijske vježbe iz predmeta NWT", new Long(1), new Long(6), new Long(7), Boolean.TRUE );
        String url = "/events-micro/events/"+event.getId().toString();
        MvcResult r = mvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertEventToJson(newEvent)))
                .andExpect(status().isOk())
                .andReturn()
                ;
        res = r.getResponse().getContentAsString();
        EventDes updatedEvent = getEventFromRes();
        if (!(updatedEvent.getTitle().equals("LV7 NWT") && updatedEvent.getDate().toString().equals("2020-04-07"))) {
            throw new AssertionError("Event not updated!");
        }
    }

    @Order(5)
    @Test
    public void updateEventByIdNoBody() throws Exception {
        EventDes event = createEvent();
        NewEvent newEvent = new NewEvent("LV7 NWT", "Zmaja od Bosne bb", LocalDate.of(2020,04,07), "Laboratorijske vježbe iz predmeta NWT", new Long(1), new Long(6), new Long(7), Boolean.TRUE );
        String url = "/events-micro/events/"+event.getId().toString();
        mvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                ;
    }


    @Order(6)
    @Test
    public void deleteEventyById() throws Exception {
        int prev = getEventsCount();
        EventDes event = createEvent();
        String url = "/events-micro/events/"+event.getId().toString();
        mvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(status().isOk());
        //if ((prev-1) != getEventsCount()) throw new AssertionError("Event not deleted");
    }

    @Order(7)
    @Test
    public void deleteEventyByIdBadRequest() throws Exception {
        int prev = getEventsCount();
        EventDes event = createEvent();
        String url = "/events-micro/events/"+event.getId().toString();
        mvc.perform(MockMvcRequestBuilders.delete(url)).andExpect(status().is5xxServerError());
        }


}
