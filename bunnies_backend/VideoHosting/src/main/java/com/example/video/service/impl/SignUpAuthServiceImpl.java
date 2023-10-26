package com.example.video.service.impl;

import com.example.video.controller.advice.exception.UserAlreadyExists;
import com.example.video.dto.TokensDTO;
import com.example.video.dto.request.JwtRequest;
import com.example.video.entity.Role;
import com.example.video.entity.User;
import com.example.video.repository.RoleRepository;
import com.example.video.repository.UserRepository;
import com.example.video.security.JwtProvider;
import com.example.video.service.SignUpAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class SignUpAuthServiceImpl implements SignUpAuthService {

    private final JwtProvider provider;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public TokensDTO signup(JwtRequest data) {
        var username = data.getUsername();
        var password = data.getPassword();
        {
            var user = userRepository.findByUsername(username);
            if (user.isPresent())
                throw new UserAlreadyExists(user.get().getId(), username);
        }
        var user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        var rs = new HashSet<Role>();
        rs.add(roleRepository.findByName("USER"));
        user.setRoles(rs);
        user = userRepository.save(user);
        var access_token = provider.generateAccessToken(user);
        var refresh_token = provider.generateRefreshToken(user);
        return TokensDTO.builder()
                .accessToken(access_token)
                .refreshToken(refresh_token)
                .build();
    }

}