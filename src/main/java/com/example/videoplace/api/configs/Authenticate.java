package com.example.videoplace.api.configs;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Configuration
public class Authenticate {

    private final Dotenv dotenv = Dotenv.load();
    private final SecretKey KEY = Keys.hmacShaKeyFor(
            dotenv.get("SECRET_KEY")
                    .getBytes(StandardCharsets.UTF_8));

    public String generateToken (String name) {
        String token = Jwts.builder()
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(
                        Date.from(
                                LocalDateTime.now().plusMinutes(60L)
                                        .atZone(ZoneId.systemDefault())
                                        .toInstant()))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
        return token;
    }
}
