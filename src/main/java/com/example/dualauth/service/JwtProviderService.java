package com.example.dualauth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtProviderService {

    @Value("${app.jwt.email.secret}")
    private String emailSecret;

    @Value("${app.jwt.mobile.secret}")
    private String mobileSecret;

    @Value("${app.jwt.email.expiration-seconds:900}")
    private long emailExpirySecs;

    @Value("${app.jwt.mobile.expiration-seconds:900}")
    private long mobileExpirySecs;

    @Value("${app.jwt.issuer:dual-auth-app}")
    private String issuer;

    private Key emailKey;
    private Key mobileKey;

    @PostConstruct
    public void init() {
        emailKey = Keys.hmacShaKeyFor(emailSecret.getBytes());
        mobileKey = Keys.hmacShaKeyFor(mobileSecret.getBytes());
    }

    public String generateEmailToken(String subject, Map<String, Object> extraClaims) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuer(issuer)
                .claim("auth_type", "EMAIL")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + emailExpirySecs * 1000))
                .signWith(emailKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateMobileToken(String subject, Map<String, Object> extraClaims) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuer(issuer)
                .claim("auth_type", "MOBILE")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + mobileExpirySecs * 1000))
                .signWith(mobileKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        // try email key first
        try {
            return Jwts.parserBuilder().setSigningKey(emailKey).build().parseClaimsJws(token).getBody();
        } catch (JwtException ex) {
            // try mobile key
        }
        return Jwts.parserBuilder().setSigningKey(mobileKey).build().parseClaimsJws(token).getBody();
    }
}
