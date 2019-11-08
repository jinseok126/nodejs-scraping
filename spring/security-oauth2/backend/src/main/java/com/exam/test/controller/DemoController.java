/**
 * 
 */
package com.exam.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author user
 *
 */
@RestController
public class DemoController {

	@Autowired
	UserDetailsService userDetailsService;
	
	@RequestMapping("/")
	public String home() {
		return "test";
	}
	
	@RequestMapping("/test")
	public String test() {
		return "test1";
	}
}
