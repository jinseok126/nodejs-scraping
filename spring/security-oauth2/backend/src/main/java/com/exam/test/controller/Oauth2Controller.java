/**
 * 
 */
package com.exam.test.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.test.vo.ParsingVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;


/**
 * @author user
 *
 */

@Slf4j
@RestController
public class Oauth2Controller {
	
	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;
	
	@RequestMapping("/")
	public Principal home(Principal principal, OAuth2AuthenticationToken authentication) throws JsonProcessingException {
		
		log.info("Oauth2Controller home");
		
		if(!principal.equals(null)) {
			// Client info
			OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
					authentication.getAuthorizedClientRegistrationId(), authentication.getName());
			
			ObjectMapper objectMapper = new ObjectMapper();
			// objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			String serialize = objectMapper.writeValueAsString(principal);
			ParsingVO parsing = objectMapper.readValue(serialize, ParsingVO.class);
			
			log.info(client.getAccessToken().getTokenValue());
			
			// true일 경우 DB의 값 비교 후 존재하지 않는 아이디일 경우 회원 가입 후 토큰 전달
			if(parsing.isAuthenticated()) {
				
			}
		} // if
		
		return principal;
	}
	
	/*
	@RequestMapping("/")
	public Principal home(Principal principal, OAuth2AuthenticationToken authentication, OAuth2Authentication auth, HttpServletResponse res) throws URISyntaxException, JsonMappingException, JsonProcessingException {
	// public ResponseEntity<String> home(Principal principal, OAuth2AuthenticationToken authentication, HttpServletResponse res) throws URISyntaxException, UnsupportedEncodingException {
		
		// google hash ID 108836775685540087010
		log.info(authentication.getName());
		
		OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
				authentication.getAuthorizedClientRegistrationId(), authentication.getName());
		
		log.info(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
		
		// code
		// log.info("zzz = "+client.getClientRegistration().getAuthorizationGrantType().getValue());
		
		log.info("AccessToken = "+client.getAccessToken().getTokenValue());	 // 이건 security에서 만든 token
		
		
		Map<String, Object> map = authentication.getPrincipal().getAttributes();
		Iterator<String> it = map.keySet().iterator();
		// 회원정보 (아이디 이메일 등)
//		while(it.hasNext()) {
//			String key = it.next();
//			log.info(key+": "+map.get(key));
//		}

		// [ROLE_USER, SCOPE_https://www.googleapis.com/auth/userinfo.email, SCOPE_https://www.googleapis.com/auth/userinfo.profile, SCOPE_openid]
		// log.info("Authorities = "+authentication.getAuthorities());
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>(authentication.getAuthorities());
		for(GrantedAuthority item: list) {
			log.info(""+item.getAuthority());
		}
		
		String uri = "http://localhost:8080";
	    URI location = new URI(uri);
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Content-Type", "text/html");
	    headers.setLocation(location);
	    
	    
	    // tomcat 8.5 이상 사용 시 오류 남 톰캣에서 특수문자를 막아놨음
	    // Cookie cookie = new Cookie("Authorization", SecurityConstants.TOKEN_PREFIX+client.getAccessToken().getTokenValue());
//	    Cookie cookie = new Cookie("Authorization", URLEncoder.encode(SecurityConstants.TOKEN_PREFIX+client.getAccessToken().getTokenValue(),"utf-8"));
//	    res.addCookie(cookie);
	    
	    // return new ResponseEntity<String>(headers, HttpStatus.SEE_OTHER);
	    
	    
//	    List list2 = new ArrayList<>(authentication.getPrincipal().getAuthorities());
//	    log.info("get = "+list2.get(0));
	    
	    
	    // log.info("auth = "+auth.getUserAuthentication());
	    
	    return principal;
	}
	 */	
}
