package com.example.video.service.impl;

import com.example.video.dto.TokensDTO;
import com.example.video.dto.request.JwtRequest;
import com.example.video.entity.User;
import com.example.video.security.JwtProvider;
import com.example.video.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider provider;

    public String refreshToken(String refreshToken) {
        provider.validateRefreshToken(refreshToken);
        var id = provider.getRefreshId(refreshToken);
        var access_token = provider.generateAccessToken(id);
        return access_token;
    }

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

}