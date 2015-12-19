/**
 * 
 */
package com.sivalabs.jcart.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivalabs.jcart.entities.Permission;

/**
 * @author Siva
 *
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer>
{

}
