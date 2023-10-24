package com.example.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@PropertySource("classpath:application-secret.properties")
@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {

    public static void main(String[] args) throws IOException {
        try (final var in = Application.class.getClassLoader().getResourceAsStream("application-secret.properties")) {
            var text = new String(in.readAllBytes());
            System.out.println(text);
        }
        SpringApplication.run(Application.class, args);
    }

}
