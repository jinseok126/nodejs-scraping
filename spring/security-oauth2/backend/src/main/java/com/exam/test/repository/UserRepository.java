/**
 * 
 */
package com.exam.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.test.entity.UserEntity;

/**
 * @author user
 *
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public UserEntity findByUserId(String userId);
}
