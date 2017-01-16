/**
 * 
 */
package com.sivalabs.jcart.admin.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.entities.User;
import com.sivalabs.jcart.security.SecurityService;

/**
 * @author rajakolli
 *
 */
@RunWith(SpringRunner.class)
public class CustomUserDetailsServiceTest
{

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @MockBean
    private SecurityService securityService;

    private CustomUserDetailsService customUserDetailsService;

    private String email = "JUNIT_EMAIL";

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        User user = new User();
        user.setEmail(email);
        user.setPassword("passwrd");
        Role role = new Role();
        role.setName("JUNIT_ROLE");
        Permission permission = new Permission();
        permission.setName("JUNIT_PERMISSION");
        List<Permission> permissions = Collections.singletonList(permission);
        role.setPermissions(permissions);
        List<Role> roles = Collections.singletonList(role);
        user.setRoles(roles);
        when(securityService.findUserByEmail(email)).thenReturn(user);
        customUserDetailsService = new CustomUserDetailsService(securityService);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.admin.security.CustomUserDetailsService#loadUserByUsername(java.lang.String)}.
     */
    @Test
    public void testLoadUserByUsername()
    {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        assertEquals(email, userDetails.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo("passwrd");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByEmail()
    {
        when(securityService.findUserByEmail(email)).thenReturn(null);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        exception.expectMessage("Email JUNIT_EMAIL not found");
        assertNull(userDetails);
    }

}
