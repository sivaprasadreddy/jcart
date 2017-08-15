/**
 * 
 */
package com.sivalabs.jcart.admin.web.validators;

import com.sivalabs.jcart.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sivalabs.jcart.entities.Role;

/**
 * @author Siva
 *
 */
@Component
public class RoleValidator implements Validator
{
	@Autowired protected MessageSource messageSource;
	@Autowired protected SecurityService securityService;
	
	@Override
	public boolean supports(Class<?> clazz)
	{
		return Role.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors)
	{
		Role role = (Role) target;
		String name = role.getName();
		Role RoleByName = securityService.getRoleByName(name);
		if(RoleByName != null){
			errors.rejectValue("name", "error.exists",new Object[]{name}, "Role "+name+" already exists");
		}
	}
	
	
}
