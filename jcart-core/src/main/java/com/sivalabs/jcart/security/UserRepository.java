/**
 * 
 */
package com.sivalabs.jcart.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivalabs.jcart.entities.User;

/**
 * @author Siva
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>
{

	User findByEmail(String email);

}
