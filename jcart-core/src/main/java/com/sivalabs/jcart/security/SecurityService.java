package com.sivalabs.jcart.security;

import java.util.List;

import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.entities.User;

public interface SecurityService
{

    User findUserByEmail(String userEmail);

    List<Permission> getAllPermissions();

    List<Role> getAllRoles();

    Role createRole(Role role);

    Role getRoleById(Integer id);

    Role updateRole(Role role);

    String resetPassword(String email);

    boolean verifyPasswordResetToken(String email, String token);

    void updatePassword(String email, String token, String encodedPwd);

    List<User> getAllUsers();

    User createUser(User user);

    User getUserById(Integer id);

    User updateUser(User user);

    Role getRoleByName(String name);

}
