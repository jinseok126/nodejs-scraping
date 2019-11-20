/**
 * 
 */
package com.exam.test.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.exam.test.provider.JwtProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@Slf4j
public class TestFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtProvider JwtProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("TestFilter doFilterInternal");
		List<String> roles = new ArrayList<>();
		roles.add("USER");
		log.info("test = "+JwtProvider.createToken("test", roles));
	}

}
