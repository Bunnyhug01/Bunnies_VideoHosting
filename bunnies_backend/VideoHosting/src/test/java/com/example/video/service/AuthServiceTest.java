package com.example.video.service;

import com.example.video.config.SecurityConfig;
import com.example.video.dto.request.SignInUserRequest;
import com.example.video.entity.User;
import com.example.video.repository.UserRepository;
import com.example.video.security.JwtProvider;
import com.example.video.service.impl.AuthServiceImpl;
import com.example.video.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = {
        AuthServiceImpl.class,
        UserServiceImpl.class,
        JwtProvider.class,
        SecurityConfig.class,
})
@TestPropertySource("classpath:application-test.properties")
public class AuthServiceTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AuthService service;
    @Autowired
    private JwtProvider provider;

    @MockBean
    private UserRepository userRepository;

    @Test
    void signin() {
        var v1 = User.builder().id(1L).username("Mihail").password("1234").build();
        Mockito.when(userRepository.findByUsername("Mihail")).thenReturn(Optional.of(v1));
        var tokens = service.signin(SignInUserRequest.builder()
                .username("Mihail")
                .password("1234")
                .build());
        var accessId = provider.getAccessId(tokens.getAccessToken());
        var refreshId = provider.getRefreshId(tokens.getRefreshToken());
        assertEquals(accessId, 1L);
        assertEquals(refreshId, 1L);
    }

    @Test
    void refreshToken() throws InterruptedException {
        var v1 = User.builder().id(1L).username("Mihail").password("1234").build();
        Mockito.when(userRepository.findByUsername(v1.getUsername())).thenReturn(Optional.of(v1));
        Mockito.when(userRepository.findById(v1.getId())).thenReturn(Optional.of(v1));
        var tokens = service.signin(SignInUserRequest.builder()
                .username(v1.getUsername())
                .password(v1.getPassword())
                .build());
        Thread.sleep(1000);
        var newToken = service.refreshToken(tokens.getRefreshToken());
        assertNotEquals(newToken, tokens.getAccessToken());
    }

}