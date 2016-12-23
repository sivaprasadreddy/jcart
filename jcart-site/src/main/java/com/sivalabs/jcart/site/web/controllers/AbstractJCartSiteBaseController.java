/**
 * 
 */
package com.sivalabs.jcart.site.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sivalabs.jcart.site.web.models.Cart;
import com.sivalabs.jcart.common.services.JCLogger;
import com.sivalabs.jcart.site.security.AuthenticatedUser;

/**
 * @author Siva
 *
 */
public abstract class JCartSiteBaseAbstractController
{
    protected final JCLogger logger = JCLogger.getLogger(getClass());

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

    @ModelAttribute("headerTitle")
    public String headerTitle()
    {
        return getHeaderTitle();
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
            return ((AuthenticatedUser) principal);
        }
        // principal object is either null or represents anonymous user -
        // neither of which our domain User object can represent - so return null
        return null;
    }

    public static boolean isLoggedIn()
    {
        return getCurrentUser() != null;
    }

    protected Cart getOrCreateCart(HttpServletRequest request)
    {
        Cart cart = null;
        cart = (Cart) request.getSession().getAttribute("CART_KEY");
        if (cart == null)
        {
            cart = new Cart();
            request.getSession().setAttribute("CART_KEY", cart);
        }
        return cart;
    }

}
