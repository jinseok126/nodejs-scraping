/**
 * 
 */
package com.exam.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.test.entity.RolesEntity;

/**
 * @author user
 *
 */
public interface RoleRepository extends JpaRepository<RolesEntity, Long> {
	public RolesEntity findByRole(String role);
}
