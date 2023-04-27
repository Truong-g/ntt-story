package com.nttstory.story.config;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApplicationConfig {
    @Bean
    public List<FilterRegistrationBean<?>> filterRegistrationBeans() {
        List<FilterRegistrationBean<?>> filterRegistrationBeans = new ArrayList<>();
        return filterRegistrationBeans;
    }

}
