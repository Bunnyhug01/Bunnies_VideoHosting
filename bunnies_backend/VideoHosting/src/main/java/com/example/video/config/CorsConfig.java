package com.example.video.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer(@Value("${cross.origin.url}") String origin) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(origin)
//                        .allowedOrigins("http://192.168.43.84:3000", "http://localhost:3000")
                        .allowedHeaders("*")
                        .maxAge(3600)
                        .allowCredentials(true);
            }
        };
    }

}