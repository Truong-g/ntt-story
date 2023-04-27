package com.nttstory.story;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class NttstoryApplication {
	public static void main(String[] args) {
		SpringApplication.run(NttstoryApplication.class, args);
	}

}
