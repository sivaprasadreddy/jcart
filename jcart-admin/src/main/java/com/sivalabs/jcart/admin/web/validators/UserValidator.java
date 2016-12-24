/**
 * 
 */
package com.sivalabs.jcart.admin.web.validators;

import static java.util.Objects.nonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sivalabs.jcart.entities.User;
import com.sivalabs.jcart.security.SecurityService;

/**
 * @author Siva
 *
 */
@Component
public class UserValidator implements Validator
{
    @Autowired
    protected MessageSource messageSource;
    @Autowired
    protected SecurityService securityService;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        User user = (User) target;
        String email = user.getEmail();
        User userByEmail = securityService.findUserByEmail(email);
        if (nonNull(userByEmail))
        {
            errors.rejectValue("email", "error.exists", new Object[] { email },
                    "Email " + email + " already in use");
        }
    }

}
