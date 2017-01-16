package com.sivalabs.jcart.site.web.controllers;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.sivalabs.jcart.site.security.AuthenticatedUser;
import com.sivalabs.jcart.site.web.models.Cart;

/**
 * @author Siva
 *
 */
public abstract class AbstractJCartSiteController
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

    protected Cart getOrCreateCart(HttpServletRequest request)
    {
        Cart cart;
        cart = (Cart) request.getSession().getAttribute("CART_KEY");
        if (isNull(cart))
        {
            cart = new Cart();
            request.getSession().setAttribute("CART_KEY", cart);
        }
        return cart;
    }

}
