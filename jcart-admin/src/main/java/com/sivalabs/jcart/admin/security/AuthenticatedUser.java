package com.sivalabs.jcart.admin.security;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.sivalabs.jcart.entities.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Siva
 * @author rajakolli
 *
 */

@EqualsAndHashCode(callSuper=true)
public class AuthenticatedUser extends org.springframework.security.core.userdetails.User
{

    private static final long serialVersionUID = 1L;

    @Getter
    private User user;

    public AuthenticatedUser(User user)
    {
        super(user.getEmail(), user.getPassword(), getAuthorities(user));
        this.user = user;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user)
    {
        return user.getRoles().stream()
                .filter(role -> nonNull(role.getPermissions()))
                .flatMap(role -> Stream.concat(
                        Stream.of(new SimpleGrantedAuthority(role.getName())),
                        role.getPermissions().stream()
                                .map(permission -> new SimpleGrantedAuthority(
                                        "ROLE_" + permission.getName()))))
                .collect(toSet());

    }

}
