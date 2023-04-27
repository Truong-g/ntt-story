package com.nttstory.story.config;


import com.nttstory.story.exception.MyAccessDeniedHandler;
import com.nttstory.story.exception.MyAuthenticationEntryPoint;
import com.nttstory.story.service.JwtService;
import com.nttstory.story.service.impl.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtService jwtService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsServiceImpl();
    }

    @Bean
    AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**").anonymous()
                //user
                .requestMatchers(HttpMethod.GET, "/user").hasAnyAuthority("ADMIN", "AUTHOR")
                .requestMatchers(HttpMethod.GET, "/user/{id}").hasAnyAuthority("ADMIN", "AUTHOR")
                .requestMatchers(HttpMethod.POST, "/user").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/user/{id}").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/user/{id}").hasAuthority("ADMIN")
                //post
                .anyRequest().denyAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new MyAccessDeniedHandler())
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
