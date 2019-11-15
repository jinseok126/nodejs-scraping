/**
 * 
 */
package com.exam.test.oauth2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@Slf4j
@SpringBootTest
public class ClientRegistrationTest {

	@Autowired
	InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository;
	
	private static List<String> clients = Arrays.asList("google", "facebook", "kakao"); 
	private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";
	
	@Autowired
	private Environment env;
	
	@Test
	public void test() {
		Iterator<ClientRegistration> it = inMemoryClientRegistrationRepository.iterator();
		while(it.hasNext()) {
			ClientRegistration key = it.next();
			log.info("key: "+key);
		}
		
		log.info(env.getProperty(CLIENT_PROPERTY_KEY+clients.get(0)+".client-id"));
		log.info(env.getProperty(CLIENT_PROPERTY_KEY+clients.get(1)+".client-id"));
		log.info(env.getProperty(CLIENT_PROPERTY_KEY+clients.get(2)+".client-id"));
	}
}
