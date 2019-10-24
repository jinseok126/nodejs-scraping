/**
 * 
 */
package com.test.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author user
 *
 */
@RestController
public class DemoController {
	
	@RequestMapping("/")
	public String demo() {
		return "Demo Controller";
	}
}
