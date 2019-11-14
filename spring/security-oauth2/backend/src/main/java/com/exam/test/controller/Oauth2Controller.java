/**
 * 
 */
package com.exam.test.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


/**
 * @author user
 *
 */
@RestController
@Slf4j
public class Oauth2Controller {
	
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;
	
	@GetMapping("/")
	public OAuth2AuthorizedClient home(Principal principal, OAuth2AuthenticationToken authentication) throws URISyntaxException {
	// public ResponseEntity<String> home(Principal principal, OAuth2AuthenticationToken authentication) throws URISyntaxException {
		
		log.info("Oauth2Controller home");
		
//		log.info("DemoController home");
//		log.info("principal = "+principal);
//		log.info("authentication = "+authentication);
		
		OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
				authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		
		String uri = "http://localhost:8080";
	    HttpHeaders headers = new HttpHeaders();
	    URI location = new URI(uri);
	    headers.setLocation(location);
	    headers.set("Authorization", "Bearer " + client.getAccessToken().getTokenValue());

		// ResponseEntity<String> response = new RestTemplate().exchange("http://localhost:8080", HttpMethod.GET);
		// ResponseEntity<String> response = new RestTemplate().exchange("https://naver.com", HttpMethod.GET, new HttpEntity<String>(header), String.class);
		    
	    // return new ResponseEntity<String>(headers, HttpStatus.SEE_OTHER);
		return client;
	}
}
