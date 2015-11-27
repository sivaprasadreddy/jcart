/**
 * 
 */
package com.sivalabs.jcart.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.jcart.entities.User;

/**
 * @author Siva
 *
 */
@Service
@Transactional
public class SecurityService
{
	@Autowired UserRepository userRepository;
	
	public User findUserByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}
	
	

}
