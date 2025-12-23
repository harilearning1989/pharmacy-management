package com.web.pharma.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.expiration-ms:3600000}")
    private long jwtExpirationMs;
    private final Key signingKey;

    public JwtProvider(
            @Value("${jwt.secret:secret}") String jwtSecret,
            @Value("${jwt.expiration-ms:3600000}") long jwtExpirationMs) {
        // HS512 requires ≥ 64 bytes
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationMs = jwtExpirationMs;
    }

    // =========================
    // TOKEN GENERATION
    // =========================
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(signingKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // =========================
    // TOKEN PARSING (ONCE)
    // =========================
    public Claims parseToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // =========================
    // TOKEN VALIDATION
    // =========================
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            Claims claims = parseToken(token); // ✅ parsed ONCE
            return claims.getSubject().equals(userDetails.getUsername()) &&
                    claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // =========================
    // CLAIM HELPERS (NO PARSING)
    // =========================
    public String extractUsername(Claims claims) {
        return claims.getSubject();
    }

    public Date extractExpiration(Claims claims) {
        return claims.getExpiration();
    }
}

