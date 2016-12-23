/**
 * 
 */
package com.sivalabs.jcart.site.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import com.sivalabs.jcart.entities.Customer;

public class AuthenticatedUser extends org.springframework.security.core.userdetails.User
{

    private static final long serialVersionUID = 1L;
    private Customer customer;

    public AuthenticatedUser(Customer customer)
    {
        super(customer.getEmail(), customer.getPassword(), getAuthorities(customer));
        this.customer = customer;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(
            Customer customer)
    {
        Collection<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList("ROLE_USER");
        return authorities;
    }
}
