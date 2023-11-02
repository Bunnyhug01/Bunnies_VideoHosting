package com.example.video.repository;

import com.example.video.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void test1() {
        var user = repository.save(User.builder()
                .username("Arseny")
                .logoUrl("https://example.com")
                .password("1234")
                .build());
        var arseny = repository.findByUsername(user.getUsername());
        assertTrue(arseny.isPresent());
    }

}
