package com.example.video;

import io.jsonwebtoken.io.Encoders;

import java.security.SecureRandom;

public class GenerateJWTSecrets {

    public static void main(String[] args) {
        var random = new SecureRandom();
        var buffer = new byte[64];
        random.nextBytes(buffer);
        System.out.println("jwt.secret.access=" + Encoders.BASE64.encode(buffer));
        random.nextBytes(buffer);
        System.out.println("jwt.secret.refresh=" + Encoders.BASE64.encode(buffer));
    }

}
