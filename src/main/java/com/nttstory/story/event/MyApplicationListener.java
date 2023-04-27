//package com.nttstory.story.event;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import java.util.Map;
//
//
////@Component
////@Slf4j
////public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
////    @Override
////    public void onApplicationEvent(ContextRefreshedEvent event) {
////        ApplicationContext applicationContext = event.getApplicationContext();
////        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
////                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
////        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
////                .getHandlerMethods();
////        map.forEach((key, value) -> log.info("{} {}", key, value));
////    }
////}
