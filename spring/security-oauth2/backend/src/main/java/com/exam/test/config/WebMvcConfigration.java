/**
 * 
 */
package com.exam.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.exam.test.interceptor.StompInterceptor;

/**
 * @author user
 *
 */
@Configuration
public class WebMvcConfigration implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				// .allowedOrigins("http://localhost:8080", "ws://localhost:8080")
				.allowedOrigins("https://spring-vue-deploy-demo.firebaseapp.com", "http://localhost:8080", "ws://spring-vue-deploy-demo.firebaseapp.com")
				.allowedMethods("OPTIONS", "POST", "GET")
				.allowedHeaders("content-type", "Access-Control-Allow-Headers", "Authorization")
				.exposedHeaders("Authorization", "status", "Location");
	}
	
	@Autowired
	StompInterceptor stompInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// registry.addInterceptor(stompInterceptor).addPathPatterns("/spring-websocket/**");
	}
}
