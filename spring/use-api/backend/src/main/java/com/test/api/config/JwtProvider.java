/**
 * 
 */
package com.test.api.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.test.api.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@Component
@Slf4j
public class JwtProvider {

	private long accessTokenValidMilisecond = 1000L * (60 * 60);	// 1시간
	SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public String createToken(UserEntity user) {
		
		Claims claims = Jwts.claims().setSubject("accessToken");
		claims.put("uid", user.getUserId());
		claims.put("urole", user.getRoleIdx().getRoleName());
		
		Date now = new Date();
		String token = Jwts.builder().
        		setHeaderParam("typ", "JWT").
        		setClaims(claims).		 // 데이터
        		setIssuedAt(now).		 // 토큰 발행일자
        		setExpiration(new Date(now.getTime() + accessTokenValidMilisecond)).		// set Expire Time
        		signWith(key).						// 암호화 알고리즘, secret값 세팅
        		compact();
		
		
		log.info("Create Token: "+token);
		
		return token;
	}
	
	public UserEntity tokenCheck(String token) {
		UserEntity user = new UserEntity();
		try {
			Jws<Claims> jws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			log.info(""+jws);
			user.setUserId(jws.getBody().getSubject());
		} catch (JwtException e) {
			log.error("tokenCheck error: "+e);
		}
		
		return user;
	}
	
	public boolean tokenValid(String token) {
		
		boolean flag = false;
		try { 
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			flag = true;
		} catch (JwtException e) {
			flag = false;
		}
		
		return flag;
	}
}
