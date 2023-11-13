package com.example.video.controller.auth;

import com.example.video.config.SecurityConfig;
import com.example.video.controller.UserController;
import com.example.video.entity.User;
import com.example.video.security.JwtProvider;
import com.example.video.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.video.security.JwtTokenAuthenticationFilter.HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
        UserController.class,
        SecurityConfig.class,
        JwtProvider.class,
})
public class AuthUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwt;

    @MockBean
    private UserService service;

    @Test
    void getMe() throws Exception {
        var id = 1L;
        var person = User.builder().id(id).username("Michail").password("1234").build();
        var token = jwt.generateAccessToken(id);
        Mockito.when(service.findById(id)).thenReturn(person);
        mvc.perform(get("/users/me").header(AUTHORIZATION, HEADER_PREFIX + token))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(person)));
    }

}