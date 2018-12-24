package com.servervaletdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Properties;

@SpringBootApplication
public class ServerValetApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		//SpringApplication app = new SpringApplication(ServerValetApplication.class);
		SpringApplication.run(ServerValetApplication.class, args);

		//Properties properties = new Properties();
		//properties.setProperty("spring.resources.static-locations", "classpath:/static/");

		//app.setDefaultProperties(properties);
		//app.run(args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**")
				.addResourceLocations("classpath:/assets/");
	}
}
