/**
 * 
 */
package com.sivalabs.jcart.admin.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.entities.User;

/**
 * @author Siva
 *
 */
public class AuthenticatedUser extends org.springframework.security.core.userdetails.User
{

	private static final long serialVersionUID = 1L;
	private User user;
	
	public AuthenticatedUser(User user)
	{
		super(user.getEmail(), user.getPassword(), getAuthorities(user));
		this.user = user;
	}
	
	public User getUser()
	{
		return user;
	}
	
	private static Collection<? extends GrantedAuthority> getAuthorities(User user)
	{
		List<Role> roles = user.getRoles();
		
		Set<GrantedAuthority> authorities = new HashSet<>();
		
		for (Role role : roles)
		{
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			List<Permission> permissions = role.getPermissions();
			for (Permission permission : permissions)
			{
				authorities.add(new SimpleGrantedAuthority("ROLE_"+permission.getName()));
			}
		}
		return authorities;
	}
}
