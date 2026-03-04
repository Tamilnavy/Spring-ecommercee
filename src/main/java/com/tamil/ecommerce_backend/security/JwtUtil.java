package com.tamil.ecommerce_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final String JWT_SECRET = "dec6017813f0ab9b67729e221cdbe776f3e4099b0423ba8289da3b6dad867b65";
    private final String JWT_REFRESH_SECRET = "refreshsecretkeyrefreshsecretkeyrefreshsecretkey";

    private final Key accessKey = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    private final Key refreshKey = Keys.hmacShaKeyFor(JWT_REFRESH_SECRET.getBytes());

    // Access token 30 seconds (like your node testing)
    public String generateAccessToken(String id, String role) {
        return Jwts.builder()
                .setSubject(id)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hour
                .signWith(accessKey)
                .compact();
    }

    public String generateRefreshToken(String id) {
        return Jwts.builder()
                .setSubject(id)
                .setExpiration(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000))
                .signWith(refreshKey)
                .compact();
    }

    public String extractUserIdFromRefresh(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(refreshKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractUserIdFromAccess(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}