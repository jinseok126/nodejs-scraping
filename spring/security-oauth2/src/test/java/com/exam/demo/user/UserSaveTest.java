/**
 * 
 */
package com.exam.demo.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.exam.demo.domain.RolesEntity;
import com.exam.demo.domain.UserEntity;
import com.exam.demo.repository.RoleRepository;
import com.exam.demo.repository.UserRepository;

/**
 * @author user
 *
 */
@SpringBootTest
public class UserSaveTest {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Test
	public void test() {
		
		String password = passwordEncoder.encode("123456");
		
		UserEntity user = new UserEntity();
		RolesEntity role = roleRepo.findByRole("ADMIN");
		
		user.setUserId("admin");
		user.setUserPw(password);
		user.setRoles(role);
		
		userRepo.save(user);
	}
}
