/**
 * 
 */
package com.exam.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author user
 *
 */
@RestController
public class DemoController {

	@RequestMapping("/")
	public String home() {
		return "test";
	}
	
	@RequestMapping("/test")
	public String test() {
		return "test1";
	}
}
