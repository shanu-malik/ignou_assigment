package com.example.assignment_api.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long jwtExpirationMs;

    public JwtUtil(
            @Value("${JWT_SECRET:}") String secret,
            @Value("${JWT_EXPIRATION_MS:3600000}") long expirationMs
    ) {
        // If the secret from env is empty, generate a random strong key
        if (secret == null || secret.isEmpty()) {
            this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        } else {
            // Make sure the key is at least 32 bytes (256 bits)
            this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        }
        this.jwtExpirationMs = expirationMs;
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
