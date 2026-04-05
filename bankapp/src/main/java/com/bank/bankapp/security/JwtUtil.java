package com.bank.bankapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecretKey;

   
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

 
    public String generateAccessToken(String email) {

        return Jwts.builder()   
                .subject(email) 
              
                .issuedAt(new Date()) 
                .expiration(
                        new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)
                )   
                .signWith(getSecretKey(),Jwts.SIG.HS256)
                .compact();  
    } 

public boolean validateToken(String token, String email) {
        String username = getUsernameFromToken(token);
        return username.equals(email);
        
    }

public String getUsernameFromToken(String token) {

            Claims claim=Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)   
                .getPayload();
               return claim.getSubject();
        }                         
    }
