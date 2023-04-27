package com.nttstory.story.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttstory.story.service.JwtService;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    final private JwtService jwtService;
    final private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");
           if(header == null || !header.startsWith("Bearer ")) {
               filterChain.doFilter(request, response);  		// If not valid, go to the next filter.
			return;
		}
            String token = getJwtFromRequest(request);
            boolean isTokenValid = jwtService.validateJwt(token);
            if (StringUtils.hasText(token) && isTokenValid) {
                Jws<Claims> jws = jwtService.parseJwt(token);
                String email = jws.getBody().getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (MalformedJwtException e) {
            responseError(response, e.getLocalizedMessage());
            return;
        } catch (ExpiredJwtException e) {
            responseError(response, e.getLocalizedMessage());
            return;
        } catch (UnsupportedJwtException e) {
            responseError(response, e.getLocalizedMessage());
            return;
        } catch (IllegalArgumentException e) {
            responseError(response, e.getLocalizedMessage());
            return;
        } catch (JwtException e) {
            responseError(response, e.getLocalizedMessage());
            return;
        } catch (Exception e) {
            responseError(response, e.getLocalizedMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
    private void responseError(HttpServletResponse response, String message) throws IOException {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("statusCode", HttpServletResponse.SC_UNAUTHORIZED);
        responseBody.put("messages", message);
        responseBody.put("data", null);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), responseBody);
    }
}
