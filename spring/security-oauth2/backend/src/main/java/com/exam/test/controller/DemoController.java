/**
 * 
 */
package com.exam.test.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;


/**
 * @author user
 *
 */
@RestController
@Slf4j
public class DemoController {
	
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;
	
	@GetMapping("/")
	public ModelAndView home(Principal principal, OAuth2AuthenticationToken authentication) {
		
		log.info("DemoController home");
		
		OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
				authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		    String userInfoEndpointUri = client.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
		    
		    ModelAndView mav = new ModelAndView();
		    if(!StringUtils.isEmpty(userInfoEndpointUri)) {
		    	// mav.setView(view);
		    }
		    
		    
//		    if (!StringUtils.isEmpty(userInfoEndpointUri)) {
//			    RestTemplate restTemplate = new RestTemplate();
//			    HttpHeaders headers = new HttpHeaders();
//			    headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());
//			    log.info(client.getAccessToken().getTokenValue());
//			    HttpEntity entity = new HttpEntity("", headers);
//			    ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
//			    Map userAttributes = response.getBody();
//			}
		    
		return mav;
	}
}
