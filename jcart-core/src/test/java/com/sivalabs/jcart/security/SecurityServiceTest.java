/**
 * 
 */
package com.sivalabs.jcart.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.sivalabs.jcart.JCartException;
import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.entities.User;

/**
 * @author rajakolli
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class SecurityServiceTest
{

    private static final String email = "JUNIT_EMAIL@EMAIL.COM";
    private static final String name = "JUNIT_NAME";
    private static final String password = "JUNIT_PASSWORD";
    private static final String description = "JUNIT_DESCRIPTION";
    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleRepository roleRepository;

    SecurityService securityService;
    private User user = new User();

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoles(new ArrayList<Role>());
        testEntityManager.persist(user);
        securityService = new SecurityService(userRepository, permissionRepository,
                roleRepository);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.security.SecurityService#resetPassword(java.lang.String)}.
     */
    @Test(expected = JCartException.class)
    public void testResetPassword()
    {
        String uuid = securityService.resetPassword(email);
        assertNotNull(uuid);
        securityService.resetPassword(name);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.security.SecurityService#updatePassword(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test(expected = JCartException.class)
    public void testUpdatePasswordInValidPassword()
    {
        securityService.updatePassword(email, "token", password);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.security.SecurityService#verifyPasswordResetToken(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testVerifyPasswordResetToken()
    {
        boolean verified = securityService.verifyPasswordResetToken(email, "token");
        assertFalse(verified);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.security.SecurityService#getAllPermissions()}.
     */
    @Test
    public void testGetAllPermissions()
    {
        List<Permission> perList = securityService.getAllPermissions();
        assertTrue(perList.isEmpty());
    }

    /**
     * Test method for {@link com.sivalabs.jcart.security.SecurityService#getAllRoles()}.
     */
    @Test
    public void testGetAllRoles()
    {
        List<Role> rolesList = securityService.getAllRoles();
        assertTrue(rolesList.isEmpty());
    }

    /**
     * Test method for {@link com.sivalabs.jcart.security.SecurityService#getAllUsers()}.
     */
    @Test
    public void testGetAllUsers()
    {
        List<User> userList = securityService.getAllUsers();
        assertFalse(userList.isEmpty());
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.security.SecurityService#createUser(com.sivalabs.jcart.entities.User)}.
     */
    @Test(expected = JCartException.class)
    public void testCreateUser()
    {
        User user = new User();
        user.setEmail("abc@abc.com");
        user.setName("Name");
        user.setPassword("Passw");
        user.setPasswordResetToken("resetToken");

        Role role = new Role();
        role.setName(name);
        List<Permission> permissions = new ArrayList<>();
        role.setPermissions(permissions);
        Role dbRole = securityService.createRole(role);
        assertThat(dbRole).isNotNull();
        dbRole.setDescription(description);
        securityService.updateRole(dbRole);
        dbRole = securityService.getRoleById(dbRole.getId());
        assertEquals(name, dbRole.getName());
        assertEquals(description, dbRole.getDescription());
        List<Role> roles = new ArrayList<>();
        roles.add(dbRole);
        user.setRoles(roles);

        User dbUser = securityService.createUser(user);
        assertThat(dbUser.getId()).isNotNull();
        securityService.updatePassword("abc@abc.com", "resetToken", password);

        User dbUser1 = securityService.getUserById(user.getId());
        assertEquals(dbUser1.getPassword(), password);

        dbUser1.setName("JUNIT1");
        User dbUser2 = securityService.updateUser(dbUser1);
        assertEquals("JUNIT1", dbUser2.getName());
        securityService.createUser(user);

    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.security.SecurityService#updateUser(com.sivalabs.jcart.entities.User)}.
     */
    @Test(expected = JCartException.class)
    public void testUpdateUser()
    {
        User newUser = new User();
        newUser.setId(50);
        securityService.updateUser(newUser);
    }
    
    @Test(expected = JCartException.class)
    public void testUpdatePassword()
    {
        securityService.updatePassword("a@a.com", null, password);
    }

}
