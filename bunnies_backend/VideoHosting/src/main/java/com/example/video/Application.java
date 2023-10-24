package com.example.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.io.IOException;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {

    public static void main(String[] args) throws IOException {
        try (final var in = Application.class.getClassLoader().getResourceAsStream("application.properties")) {
            var text = new String(in.readAllBytes());
            System.out.println(text);
        }
        SpringApplication.run(Application.class, args);
    }

}
