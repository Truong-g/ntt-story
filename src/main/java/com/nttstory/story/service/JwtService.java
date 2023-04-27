package com.nttstory.story.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {

    String generateJwt(UserDetails userDetails, Date expiration, Date issuedAt, String id);

    boolean validateJwt(String token);

    Jws<Claims> parseJwt(String token);

}
