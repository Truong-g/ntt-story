package com.nttstory.story.service.impl;


import com.nttstory.story.service.JwtService;
import com.nttstory.story.util.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;



@Service
public class JwtServiceImpl implements JwtService {
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public String generateJwt(UserDetails userDetails, Date expiration, Date issuedAt, String id) {
        SecretKey key = getSignInKey();
        String jws = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer(jwtConfig.getJwtIssuer())
                .setAudience(jwtConfig.getJwtAudience())
                .setExpiration(expiration)
                .setIssuedAt(issuedAt)
                .setId(id)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return jws;
    }

    @Override
    public Jws<Claims> parseJwt(String token) {
        SecretKey key = getSignInKey();
        Jws<Claims> jws = Jwts.parserBuilder()  // (1)
                .setSigningKey(key)         // (2)
                .build()                    // (3)
                .parseClaimsJws(token); // (4)
        return jws;
    }


    public boolean validateJwt(String token) {
        SecretKey key = getSignInKey();
        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return true;
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getJwtSecretKey().getBytes(StandardCharsets.UTF_8));
    }
}
