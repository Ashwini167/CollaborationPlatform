package com.niit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.niit")
public class WebResolver {
	
	@Bean
	public InternalResourceViewResolver getViewResolver() {
		InternalResourceViewResolver internalViewResolver = new InternalResourceViewResolver();
		internalViewResolver.setPrefix("/WEB-INF/jsp");
		internalViewResolver.setSuffix(".jsp");
		return internalViewResolver;
	}
}
