package com.nttstory.story.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtConfig {
    @Value("${spring.application.name}")
    private String jwtIssuer;
    @Value("${spring.application.name}")
    private String jwtAudience;

    @Value("${jwt.expiration}")
    private int jwtExpiration;
    @Value("${jwt.secret-key}")
    private String jwtSecretKey;
}
