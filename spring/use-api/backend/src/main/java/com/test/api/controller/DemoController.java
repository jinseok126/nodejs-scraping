/**
 * 
 */
package com.test.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.api.config.JwtProvider;
import com.test.api.entity.UserEntity;
import com.test.api.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@RestController
@Slf4j
public class DemoController {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	JwtProvider jwt;
	
	@RequestMapping("/")
	public String demo() {
		
		UserEntity user = userRepo.findByUserId("test");;
		
		String token = jwt.createToken(user);
		log.info(""+jwt.tokenCheck(token));
		return token;
	}
}
