/**
 * 
 */
package com.exam.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.demo.domain.RolesEntity;

/**
 * @author user
 *
 */
public interface RoleRepository extends JpaRepository<RolesEntity, Long> {
	public RolesEntity findByRole(String role);
}
