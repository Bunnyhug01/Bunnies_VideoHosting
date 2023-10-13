package com.example.video.config;

import com.example.video.security.JwtTokenAuthenticationFilter;
import com.example.video.security.JwtTokenProvider;
import com.example.video.security.UserAuthenticationToken;
import com.example.video.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    SecurityFilterChain springWebFilterChain(HttpSecurity http,
                                             CustomUserDetailsService userDetailsService, JwtTokenProvider tokenProvider) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/auth/**").permitAll()
//                                .anyRequest().authenticated()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtTokenAuthenticationFilter(tokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager customAuthenticationManager(CustomUserDetailsService userDetailsService, PasswordEncoder encoder) {
        return authentication -> {
            var username = authentication.getPrincipal().toString();
            var password = authentication.getCredentials().toString();
            var user = userDetailsService.loadUserByUsername(username);
            if (!encoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Bad credentials");
            }
            if (!user.isEnabled()) {
                throw new DisabledException("User account is not active");
            }
            return new UserAuthenticationToken(user);
        };
    }

}