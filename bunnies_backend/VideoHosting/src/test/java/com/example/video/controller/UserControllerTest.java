package com.example.video.controller;

import com.example.video.controller.advice.exception.UserNotFoundException;
import com.example.video.entity.User;
import com.example.video.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;

    @Test
    void getOne() throws Exception {
        var person = User.builder().username("Michail").password("1234").build();
        var id = 1L;
        Mockito.when(service.getOneUser(id)).thenReturn(person);
        mvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(person)));
    }

    @Test
    void getOneNotFound() throws Exception {
        var id = 1L;
        Mockito.when(service.getOneUser(id)).thenThrow(new UserNotFoundException(id));
        mvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {
        var person = User.builder().username("Michail").password("1234").build();
        Mockito.when(service.getAllUsers()).thenReturn(List.of(person));
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(person))));
    }

}