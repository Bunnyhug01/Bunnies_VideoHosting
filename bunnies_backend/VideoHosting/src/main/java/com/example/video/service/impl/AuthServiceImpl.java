package com.example.video.service.impl;

import com.example.video.controller.advice.exception.UserAlreadyExists;
import com.example.video.controller.request.JwtRequest;
import com.example.video.dto.TokensDTO;
import com.example.video.entity.Role;
import com.example.video.entity.User;
import com.example.video.repository.RoleRepository;
import com.example.video.repository.UserRepository;
import com.example.video.security.JwtProvider;
import com.example.video.service.AuthService;
import com.example.video.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider provider;

    private final UserService users;

    @Override
    public TokensDTO signin(JwtRequest request) {
        try {
            var username = request.getUsername();
            var password = request.getPassword();
            var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = (User) authentication.getPrincipal();
            var access_token = provider.generateAccessToken(user);
            var refresh_token = provider.generateRefreshToken(user);
            return TokensDTO.builder()
                    .accessToken(access_token)
                    .refreshToken(refresh_token)
                    .build();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied", e);
        }
    }

    public String refreshToken(String refreshToken) {
        provider.validateRefreshToken(refreshToken);
        var id = provider.getRefreshId(refreshToken);
        var user = users.findById(id);
        var access_token = provider.generateAccessToken(user);
        return access_token;
    }

}