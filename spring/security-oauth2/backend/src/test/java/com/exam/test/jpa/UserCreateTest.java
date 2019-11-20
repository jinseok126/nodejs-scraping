/**
 * 
 */
package com.exam.test.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.exam.test.entity.RolesEntity;
import com.exam.test.entity.UserEntity;
import com.exam.test.repository.RoleRepository;
import com.exam.test.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@Slf4j
@SpringBootTest
public class UserCreateTest {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Test
	public void test() {
		
		String roleName = "ADMIN";
		String userId = "test2";
		String userPw = passwordEncoder.encode("123456");
		
		RolesEntity role = roleRepo.findByRole(roleName);
		UserEntity user = new UserEntity();
		user.setUserId(userId);
		user.setUserPw(userPw);
		
		if(StringUtils.isEmpty(role)) {
			RolesEntity createRole = new RolesEntity();
			createRole.setRole(roleName);
			
			roleRepo.save(createRole);
			user.setRoles(roleRepo.findByRole(roleName));
		} else {
			user.setRoles(role);
		}
		
		try {
			userRepo.save(user);
		} catch (Exception e) {
			log.error("error user create");
		}
	}
}
