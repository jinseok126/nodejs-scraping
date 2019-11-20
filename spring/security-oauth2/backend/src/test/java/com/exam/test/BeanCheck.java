/**
 * 
 */
package com.exam.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@SpringBootTest
@Slf4j
public class BeanCheck {
	
	@Autowired 
	DefaultListableBeanFactory df;
	
	@Test
	public void test() {
		log.info("#######################################################");
		for(String name: df.getBeanDefinitionNames()) {
			log.info(name+"\t "+df.getBean(name).getClass().getName());
		}
	}

}
