package com.example.video.service;

import com.example.video.controller.advice.UserNotFoundException;
import com.example.video.entity.User;
import com.example.video.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));
    }

    public User findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}