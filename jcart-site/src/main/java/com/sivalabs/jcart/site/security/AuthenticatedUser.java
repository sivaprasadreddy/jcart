package com.sivalabs.jcart.site.security;

import static java.util.Collections.singletonList;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.sivalabs.jcart.entities.Customer;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author rajakolli
 *
 */
@EqualsAndHashCode(callSuper = true)
public class AuthenticatedUser extends org.springframework.security.core.userdetails.User
{

    private static final long serialVersionUID = 1L;

    @Getter
    private Customer customer;

    public AuthenticatedUser(Customer customer)
    {
        super(customer.getEmail(), customer.getPassword(),
                singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.customer = customer;
    }

}
