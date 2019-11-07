package com.exam.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
public class UserController {

	@GetMapping({"/demo", "/"})
	public String demo() {
		return "User DEMO PAGE";
	}
}
