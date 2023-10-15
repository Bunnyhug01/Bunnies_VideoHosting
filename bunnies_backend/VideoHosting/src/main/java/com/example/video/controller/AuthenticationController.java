package com.example.video.controller;

import com.example.video.controller.advice.UserAlreadyExists;
import com.example.video.dto.TokensDTO;
import com.example.video.entity.Role;
import com.example.video.entity.User;
import com.example.video.repository.RoleRepository;
import com.example.video.repository.UserRepository;
import com.example.video.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.HashSet;

@CrossOrigin("${cross.origin.url}")
@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final RoleRepository roles;
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/auth/base/login")
    public TokensDTO login(@RequestBody AuthenticationRequest data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = (User) authentication.getPrincipal();
            var token = jwtTokenProvider.createToken(user.getId());
            return new TokensDTO(token);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied", e);
        }
    }

    @PostMapping("/auth/base/logup")
    public TokensDTO logup(@RequestBody AuthenticationRequest data) {
        var username = data.getUsername();
        var password = data.getPassword();
        {
            var user = users.findByUsername(username);
            if (user.isPresent())
                throw new UserAlreadyExists(user.get().getId(), username);
        }
        var user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        var rs = new HashSet<Role>();
        rs.add(roles.findByAuthority("USER"));
        user.setRoles(rs);
        user = users.save(user);
        var token = jwtTokenProvider.createToken(user.getId());
        return new TokensDTO(token);
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