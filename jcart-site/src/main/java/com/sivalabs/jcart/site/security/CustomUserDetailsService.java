package com.sivalabs.jcart.site.security;

import static java.util.Objects.isNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.jcart.customers.CustomerService;
import com.sivalabs.jcart.entities.Customer;

/**
 * @author Siva
 *
 */
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String email)
    {
        Customer customer = customerService.getCustomerByEmail(email);
        if (isNull(customer))
        {
            throw new UsernameNotFoundException("Email " + email + " not found");
        }
        return new AuthenticatedUser(customer);
    }

}
