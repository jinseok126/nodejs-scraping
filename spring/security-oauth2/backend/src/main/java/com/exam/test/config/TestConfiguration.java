/**
 * 
 */
package com.exam.test.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.exam.test.filter.TestFilter;


/**
 * @author user
 *
 */
@Configuration
public class TestConfiguration {
	
	@Bean
	public FilterRegistrationBean<TestFilter> stompFilter() {
		FilterRegistrationBean<TestFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new TestFilter());
		bean.setUrlPatterns(Arrays.asList("/spring-websocket/*"));
		return bean;
	}
}
