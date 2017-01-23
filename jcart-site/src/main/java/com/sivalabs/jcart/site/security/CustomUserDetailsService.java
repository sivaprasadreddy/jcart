package com.sivalabs.jcart.site.security;

import static java.util.Objects.isNull;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.jcart.customers.CustomerService;
import com.sivalabs.jcart.entities.Customer;

import lombok.RequiredArgsConstructor;

/**
 * @author Siva
 *
 */
@RequiredArgsConstructor
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService
{
    private final CustomerService customerService;

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
