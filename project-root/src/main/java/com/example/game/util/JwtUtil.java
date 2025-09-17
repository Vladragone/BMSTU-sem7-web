package com.example.game.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "YourSecretKeyForJWTGenerationDontShareThis";
    private static final long EXPIRATION_TIME = 86400000;

    public static String generateToken(String username, String role, Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("user_id", userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }
}
