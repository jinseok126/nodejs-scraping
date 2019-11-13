/**
 * 
 */
package com.exam.test;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@SpringBootTest
@Slf4j
public class TestClass {

	@Test
	public void test() {
		String token = Jwts.builder()
				.signWith(Keys.hmacShaKeyFor("270a7094a6c9479da45eb72e2ebd75f8".getBytes()))
				.setSubject("userID")
				.setExpiration(new Date(System.currentTimeMillis() + 864000000))
				.compact();
		
		log.info("token = "+token);
	}
}
