/**
 * 
 */
package com.exam.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author user
 *
 */
@Configuration
public class WebMvcConfigration implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:8080")
				.allowedMethods("OPTIONS", "POST", "GET")
				.allowedHeaders("content-type", "Access-Control-Allow-Headers")
				.exposedHeaders("Authorization");
								 	
	}
}
