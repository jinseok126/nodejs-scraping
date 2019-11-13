/**
 * 
 */
package com.exam.test.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		
		log.info("JwtAuthenticationFilter attemptAuthentication");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		
		return authenticationManager.authenticate(authenticationToken);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
											FilterChain filterChain, Authentication authentication) {
		
		log.info("JwtAuthenticationFilter successfulAuthentication");
		
		UserDetails user = (UserDetails) authentication.getPrincipal();
		
		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		for(String role:roles) {
			log.info("role = "+role);
		}
		
		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
		
		String token = Jwts.builder()
			.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
			.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
			.setIssuer(SecurityConstants.TOKEN_ISSUER)
			.setAudience(SecurityConstants.TOKEN_AUDIENCE)
			.setSubject(user.getUsername())
			// .setExpiration(new Date(System.currentTimeMillis() + 864000000))
			.setExpiration(new Date(System.currentTimeMillis() + 864000000))
			.claim("rol", roles)
			.compact();
		
		response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
		response.setHeader("test", "################################");
		
	
	}
}
