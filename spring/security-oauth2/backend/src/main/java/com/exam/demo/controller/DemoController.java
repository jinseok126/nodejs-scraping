/**
 * 
 */
package com.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author user
 *
 */

@Controller
public class DemoController {

	@RequestMapping({"/", "/home"})
	public String home() {
		return "home.html";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "user.html";
	}
	
	@RequestMapping("/admin")
	public String admin() {
		return "admin.html";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login.html";
	}
}
