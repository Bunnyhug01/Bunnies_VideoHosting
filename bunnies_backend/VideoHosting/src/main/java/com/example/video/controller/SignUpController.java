package com.example.video.controller;

import com.example.video.dto.request.SignInUserRequest;
import com.example.video.dto.response.JwtResponse;
import com.example.video.service.SignUpAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("${cross.origin.url}")
@RestController
@RequiredArgsConstructor
public class SignUpController {

    private static final String REFRESH_TOKEN = "refresh_token";
    private final Logger LOG = LoggerFactory.getLogger(SignUpController.class);

    private final SignUpAuthService signUpAuthService;

    @PostMapping("/auth/base/signup")
    public JwtResponse signup(@RequestBody SignInUserRequest request, HttpServletResponse response) {
        LOG.debug("signup " + request);
        var tokens = signUpAuthService.signup(request);
        return AuthenticationController.getJwtResponse(response, tokens);
    }

}