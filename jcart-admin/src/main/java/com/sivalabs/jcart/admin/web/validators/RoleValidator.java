/**
 * 
 */
package com.sivalabs.jcart.admin.web.validators;

import static java.util.Objects.nonNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.security.SecurityService;

import lombok.RequiredArgsConstructor;

/**
 * @author Siva
 *
 */
@Component
@RequiredArgsConstructor
public class RoleValidator implements Validator
{
    private final SecurityService securityService;

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
        Role roleByName = securityService.getRoleByName(name);
        if (nonNull(roleByName))
        {
            errors.rejectValue("name", "error.exists", new Object[] { name },
                    "Role " + name + " already exists");
        }
    }

}
