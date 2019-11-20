/**
 * 
 */
package com.exam.test.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.exam.test.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@Slf4j
@SpringBootTest
public class JwtValidTest {
	
	@Test
	public void test() {
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		Jws<Claims> jws = Jwts.parser().setSigningKey(signingKey).parseClaimsJws("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYXBpIiwiYXVkIjoic2VjdXJlLWFwcCIsInN1YiI6InRlc3QiLCJleHAiOjE1NzQyMjEyODAsInJvbCI6WyJVU0VSIl19.eWo7DoA-CV8_M2eNpW9oKhCIZ1FtVJlBAYk2_1FmpLu4J71f40n8Sv5XHEI8-A_N8mrR1kINAcP9GYtECisjgQ");
		
		log.info("#################################");
		log.info("test = "+jws);
	}
}
