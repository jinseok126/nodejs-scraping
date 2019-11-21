/**
 * 
 */
package com.exam.test.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@Slf4j
@RestController
// @Controller
public class DemoController {

	@RequestMapping("/test")
	public void test(HttpServletRequest req, HttpServletResponse res) throws URISyntaxException, IOException {
		log.info("DemoController test");
	}
	
	@RequestMapping("test1")
	public void test(HttpServletResponse res) {
	// public ResponseEntity<String> test(HttpServletResponse res) throws URISyntaxException {
		log.info("DemoController test1");
		// res.addHeader("Authorization", "Bearer test");
//		Cookie cookie = new Cookie("test1", "cookieValue");
//		res.addCookie(cookie);
//		
//		HttpHeaders responseHeaders = new HttpHeaders();
//	    responseHeaders.set("Authorization", "");
//	    responseHeaders.setLocation(new URI("http://localhost:8080"));
	    
//		return ResponseEntity.SEE()
//			      .headers(responseHeaders)
//			      .body("Response with header using ResponseEntity");
	    
	    // return new ResponseEntity<String>(responseHeaders, HttpStatus.SEE_OTHER);
	}
}
