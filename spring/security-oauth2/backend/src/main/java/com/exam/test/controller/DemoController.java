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

	@RequestMapping("/test")
	public void test() {
	}
}
