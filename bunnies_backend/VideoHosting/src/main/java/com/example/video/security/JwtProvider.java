package com.example.video.security;

import com.example.video.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;
    private final int accessValidityInSeconds;
    private final int refreshValidityInSeconds;

    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret,
            @Value("${jwt.access.validityInSeconds}") int accessValidityInSeconds,
            @Value("${jwt.refresh.validityInSeconds}") int refreshValidityInSeconds
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.accessValidityInSeconds = accessValidityInSeconds;
        this.refreshValidityInSeconds = refreshValidityInSeconds;
    }

    public String generateAccessToken(User user) {
        return generateAccessToken(user.getId());
    }

    public String generateAccessToken(Long userId) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusSeconds(accessValidityInSeconds).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .subject("" + userId)
                .expiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return generateRefreshToken(user.getId());
    }

    public String generateRefreshToken(Long userId) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusSeconds(refreshValidityInSeconds).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .subject("" + userId)
                .expiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean isValidateAccessToken(String accessToken) {
        return isValidateToken(accessToken, jwtAccessSecret);
    }

    private boolean isValidateToken(String token, Key secret) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidateRefreshToken(String refreshToken) {
        return isValidateToken(refreshToken, jwtRefreshSecret);
    }

    public Long getAccessId(String token) {
        return Long.parseLong(getAccessClaims(token).getSubject());
    }

    public Claims getAccessClaims(String token) {
        return getClaims(token, jwtAccessSecret);
    }

    private Claims getClaims(String token, Key secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public long getRefreshId(String token) {
        return Long.parseLong(getRefreshClaims(token).getSubject());
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(token, jwtRefreshSecret);
    }

}