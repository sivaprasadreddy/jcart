/**
 * 
 */
package com.sivalabs.jcart.admin.web.controllers;

import static java.util.Objects.nonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sivalabs.jcart.admin.security.AuthenticatedUser;

/**
 * @author Siva
 *
 */
public abstract class AbstractJCartAdminController
{
    @Autowired
    protected MessageSource messageSource;

    protected abstract String getHeaderTitle();

    public String getMessage(String code)
    {
        return messageSource.getMessage(code, null, null);
    }

    public String getMessage(String code, String defaultMsg)
    {
        return messageSource.getMessage(code, null, defaultMsg, null);
    }

    @ModelAttribute("authenticatedUser")
    public AuthenticatedUser authenticatedUser(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser)
    {
        return authenticatedUser;
    }

    public static AuthenticatedUser getCurrentUser()
    {

        Object principal = SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (principal instanceof AuthenticatedUser)
        {
            return (AuthenticatedUser) principal;
        }
        // principal object is either null or represents anonymous user -
        // neither of which our domain User object can represent - so return null
        return null;
    }

    public static boolean isLoggedIn()
    {
        return nonNull(getCurrentUser());
    }
}
