/**
 * 
 */
package com.sivalabs.jcart.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivalabs.jcart.entities.Role;

/**
 * @author Siva
 *
 */
public interface RoleRepository extends JpaRepository<Role, Integer>
{

    Role findByName(String name);

}
