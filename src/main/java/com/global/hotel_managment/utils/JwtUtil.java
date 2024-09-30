package com.global.hotel_managment.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final String secret = "WywAKm3n6froDRNeUmCTDJtKKPWCbBmV";
    private final SecretKey key;
    public JwtUtil(){
         this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    public String generateToken(String username) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
//        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject(); // deprecated
        try {
            return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        } catch (JwtException e) {
            // Handle invalid token (expired, tampered, etc.)
            throw new IllegalArgumentException("Invalid token");
        }
    }


    public Boolean isTokenValid(String token, String username) {
        return (extractUsername(token).equals(username) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {

        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .before(new Date());
        } catch (JwtException e) {
            // Handle invalid token (expired, tampered, etc.)
            throw new IllegalArgumentException("Invalid token");
        }
    }
}
