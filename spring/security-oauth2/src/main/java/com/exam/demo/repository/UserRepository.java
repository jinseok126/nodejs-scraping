/**
 * 
 */
package com.exam.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.demo.domain.UserEntity;

/**
 * @author user
 *
 */
// @Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public UserEntity getUserByUserId(String userId);
}
