package com.sivalabs.jcart.security;

import com.sivalabs.jcart.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siva
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>
{

	User findByEmail(String email);

}
