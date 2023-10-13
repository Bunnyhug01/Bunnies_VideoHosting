package com.example.video.security;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtProperties {

    private String secretKey = "rzxlszyykpbgqcflzxsqcysyhljt";

    // validity in milliseconds
    private long validityInMs = 3600000; // 1h

}