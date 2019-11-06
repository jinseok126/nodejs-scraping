/**
 * 
 */
package com.exam.demo.exception;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@Component
@Slf4j
public class SecurityExceptionHandler {
	
	@EventListener
	public void handleBadCredential(AuthenticationFailureBadCredentialsEvent event) {
		log.info(event.getAuthentication().getPrincipal()+ " 로그인 시도");
	}

}
