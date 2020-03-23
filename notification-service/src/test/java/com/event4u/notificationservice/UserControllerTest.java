package com.event4u.notificationservice;

import com.event4u.notificationservice.controller.UserController;
import com.event4u.notificationservice.model.User;
import com.event4u.notificationservice.repository.UserRepository;
import com.event4u.notificationservice.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService service;

    @Test
    public void getUserById()
            throws Exception {
        User alex = new User(Long.parseLong("1"));

        List<User> allUsers = Arrays.asList(alex);

        given(service.findAll()).willReturn(allUsers);

         mvc.perform(get("/users/12")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                 .andExpect((ResultMatcher) jsonPath("$", hasSize(1)))
                 .andExpect((ResultMatcher) jsonPath("$[0].userId", Long.parseLong("1")));
    }
}
