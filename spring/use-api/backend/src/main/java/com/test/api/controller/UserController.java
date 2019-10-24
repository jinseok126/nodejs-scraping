/**
 * 
 */
package com.test.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.api.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("test")
	public Object test() {
		log.info(""+userRepo.findAll());
		return userRepo.findAll();
	}
}
