/**
 * 
 */
package com.exam.test.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exam.test.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author user
 *
 */
@SpringBootTest
@Slf4j
public class FindTest {

	@Autowired
	UserRepository userRepo;
	
	@Test
	public void test() {
		log.info("userId = "+userRepo.findByUserId("test"));
	}
}
