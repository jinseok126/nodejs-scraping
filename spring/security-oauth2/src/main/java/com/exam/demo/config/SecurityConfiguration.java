/**
 * 
 */
package com.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.exam.demo.security.JwtAuthenticationFilter;
import com.exam.demo.security.JwtAuthorizationFilter;
import com.exam.demo.service.UserService;

/**
 * @author user
 *
 */

@Configuration
@EnableWebSecurity // web.xml의 springSecurityFilterChain
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired	
	UserService userService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		String password = passwordEncoder().encode("123456");
		
		auth.inMemoryAuthentication()
			.withUser("test").password(password).roles("USER").and()
			.withUser("admin").password(password).roles("ADMIN");
		// auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		// return NoOpPasswordEncoder.getInstance(); 	// deprecated 비 암호화 사용
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		// security login page 사용
		// 이건 걍 백앤드 프론트앤드 나누지 않은 버전
		http.authorizeRequests()
			.antMatchers("/", "/home").permitAll() // 인증이 필요하지 않는 부분 
			// anonymous() 시 인증 정보가 존재할 경우 인증이 필요하지 않는 부분에는 접근할 수 없어서 permitAll()로 바꿈
			
			// 인증이 필요한 부분
			// hasAuthority 사용 시 prefix로 ROLE_ 이 붙음
			// security에서 role을 꺼내서 비교할 때 접두사로 ROLE_이 붙는다
			.antMatchers("/user").access("hasAuthority('USER') or hasRole('ADMIN')")
			.antMatchers("/admin").hasAuthority("ADMIN")
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.and().httpBasic();
		*/
//		http.sessionManagement()	
//			.maximumSessions(1)					// 같은 아이디로 1명만 로그인 할 수 있음
//			.maxSessionsPreventsLogin(false);	// 신규 로그인 사용자의 로그인이 허용되고, 기존 사용자는 세션아웃 됨
		
		/*
		// custom login page 사용
		http.authorizeRequests()
			.antMatchers("/", "/home").permitAll()
			.antMatchers("/user").hasRole("USER")
			.antMatchers("/admin").hasRole("ADMIN")
			.anyRequest()
			.authenticated()
			.and()
			.formLogin().loginPage("http://localhost:8080/login")
			.permitAll();

		http.cors().and()
			.csrf().disable();	// 개발 시 에만
		*/
		
		http.authorizeRequests()
			.antMatchers("/", "/home").permitAll() // 인증이 필요하지 않는 부분 
			.antMatchers("/user").access("hasAuthority('USER') or hasRole('ADMIN')")
			.antMatchers("/admin").hasAuthority("ADMIN")
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.and().httpBasic();
		
		http.addFilter(new JwtAuthenticationFilter(authenticationManager()))
			.addFilter(new JwtAuthorizationFilter(authenticationManager()))
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
}
