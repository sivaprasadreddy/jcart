/**
 * 
 */
package com.sivalabs.jcart.admin.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.jcart.entities.User;
import com.sivalabs.jcart.security.SecurityService;

/**
 * @author Siva
 *
 */
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService
{

    private SecurityService securityService;

    /**
     * @param securityService
     */
    public CustomUserDetailsService(SecurityService securityService)
    {
        this.securityService = securityService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName)
    {
        User user = securityService.findUserByEmail(userName);
        if (user == null)
        {
            throw new UsernameNotFoundException("Email " + userName + " not found");
        }
        return new AuthenticatedUser(user);
    }

}
