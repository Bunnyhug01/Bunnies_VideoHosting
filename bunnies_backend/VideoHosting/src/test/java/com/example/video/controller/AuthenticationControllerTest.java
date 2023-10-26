package com.example.video.controller;

import com.example.video.controller.advice.exception.UserNotFoundException;
import com.example.video.dto.TokensDTO;
import com.example.video.dto.request.JwtRequest;
import com.example.video.dto.response.JwtResponse;
import com.example.video.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService service;

    @Test
    void signin() throws Exception {
        var user = new JwtRequest("Andrey", "1234");
        Mockito.when(service.signin(user)).thenReturn(TokensDTO.builder().accessToken("accessToken").refreshToken("refreshToken").build());
        mvc.perform(post("/auth/base/signin")
                        .content(objectMapper.writeValueAsBytes(user))
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(new JwtResponse("accessToken"))));
    }

    @Test
    void signinNotFound() throws Exception {
        var user = new JwtRequest("Andrey", "1234");
        Mockito.when(service.signin(user)).thenThrow(new UserNotFoundException("Andrey"));
        mvc.perform(post("/auth/base/signin")
                        .content(objectMapper.writeValueAsBytes(user))
                        .contentType("application/json")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void signinWrongPassword() throws Exception {
        Mockito.when(service.signin(new JwtRequest("Andrey", "qwerty"))).thenThrow(new BadCredentialsException("wrong password"));
        mvc.perform(post("/auth/base/signin")
                        .content(objectMapper.writeValueAsBytes(new JwtRequest("Andrey", "qwerty")))
                        .contentType("application/json")
                )
                .andExpect(status().isUnauthorized());
    }

}