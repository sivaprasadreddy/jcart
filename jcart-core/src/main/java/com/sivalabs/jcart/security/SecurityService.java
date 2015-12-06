/**
 * 
 */
package com.sivalabs.jcart.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sivalabs.jcart.JCartException;
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
	
	public String resetPassword(String email)
	{
		User user = findUserByEmail(email);
		if(user == null)
		{
			throw new JCartException("Invalid email address");
		}
		String uuid = UUID.randomUUID().toString();
		user.setPasswordResetToken(uuid);
		return uuid;
	}

	public void updatePassword(String email, String token, String password)
	{
		User user = findUserByEmail(email);
		if(user == null)
		{
			throw new JCartException("Invalid email address");
		}
		if(!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())){
			throw new JCartException("Invalid password reset token");
		}
		user.setPassword(password);
		user.setPasswordResetToken(null);
	}

	public boolean verifyPasswordResetToken(String email, String token)
	{
		User user = findUserByEmail(email);
		if(user == null)
		{
			throw new JCartException("Invalid email address");
		}
		if(!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken())){
			return false;
		}
		return true;
	}

}
