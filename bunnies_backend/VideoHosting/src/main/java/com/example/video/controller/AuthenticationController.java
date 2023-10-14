package com.example.video.controller;

import com.example.video.entity.User;
import com.example.video.repository.UserRepository;
import com.example.video.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin("${cross.origin.url}")
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository users;

    @PostMapping("/auth/base/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = (User) authentication.getPrincipal();
            var token = jwtTokenProvider.createToken(user.getId());
            Map<Object, Object> model = new HashMap<>();
            model.put("access_token", token);
            return ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied", e);
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthenticationRequest implements Serializable {

        private static final long serialVersionUID = -6986746375915710855L;
        private String username;
        private String password;

    }

}