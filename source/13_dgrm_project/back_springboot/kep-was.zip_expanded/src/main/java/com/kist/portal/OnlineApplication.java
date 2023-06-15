package com.kist.portal;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.kist.portal.sysconf.SessionListener;

@SpringBootApplication(scanBasePackages = "com.kist.portal")
public class OnlineApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OnlineApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OnlineApplication.class);
	}

	@Bean
	public HttpSessionListener httpSessionListener(){
		return new SessionListener();
	}
}
