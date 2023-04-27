package com.nttstory.story.util;

import com.nttstory.story.service.MediaService;
import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Component;

import org.springframework.context.ApplicationContext;



@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Resource
    MediaService mediaService;

//    @Autowired
//    private ApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        mediaService.init();
//        String[] filterBeanNames = context.getBeanNamesForType(Filter.class);

    }
}
