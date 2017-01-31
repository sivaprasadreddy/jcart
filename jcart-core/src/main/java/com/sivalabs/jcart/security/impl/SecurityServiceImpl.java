package com.sivalabs.jcart.security.impl;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sivalabs.jcart.JCartException;
import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.entities.User;
import com.sivalabs.jcart.security.PermissionRepository;
import com.sivalabs.jcart.security.RoleRepository;
import com.sivalabs.jcart.security.SecurityService;
import com.sivalabs.jcart.security.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author rajakolli
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService
{

    private static final String INVALID_EMAILADDRESS = "Invalid email address";
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public User findUserByEmail(String userEmail)
    {
        return userRepository.findByEmail(userEmail);
    }

    @Override
    public List<Permission> getAllPermissions()
    {
        return permissionRepository.findAll();
    }

    @Override
    public List<Role> getAllRoles()
    {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role role)
    {
        Role roleByName = getRoleByName(role.getName());
        if (nonNull(roleByName))
        {
            throw new JCartException("Role " + role.getName() + " already exist");
        }
        List<Permission> persistedPermissions = role.getPermissions().stream()
                .filter(permission -> nonNull(permission.getId()))
                .map(permission -> permissionRepository.findOne(permission.getId()))
                .collect(toList());
        role.setPermissions(persistedPermissions);
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Integer id)
    {
        return roleRepository.findOne(id);
    }

    @Override
    public Role updateRole(Role role)
    {
        Role persistedRole = getRoleById(role.getId());
        if (isNull(persistedRole))
        {
            throw new JCartException("Role " + role.getId() + " doesn't exist");
        }
        persistedRole.setDescription(role.getDescription());

        List<Permission> updatedPermissions = role.getPermissions().stream()
                .filter(permission -> nonNull(permission.getId()))
                .map(permission -> permissionRepository.findOne(permission.getId()))
                .collect(toList());
        persistedRole.setPermissions(updatedPermissions);
        return roleRepository.save(persistedRole);
    }

    @Override
    public String resetPassword(String email)
    {
        User user = findUserByEmail(email);
        if (isNull(user))
        {
            throw new JCartException(INVALID_EMAILADDRESS);
        }
        String uuid = UUID.randomUUID().toString();
        user.setPasswordResetToken(uuid);
        return uuid;
    }

    @Override
    public boolean verifyPasswordResetToken(String email, String token)
    {
        User user = findUserByEmail(email);
        if (isNull(user))
        {
            throw new JCartException(INVALID_EMAILADDRESS);
        }
        if (!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken()))
        {
            return false;
        }
        return true;
    }

    @Override
    public void updatePassword(String email, String token, String encodedPwd)
    {
        User user = findUserByEmail(email);
        if (isNull(user))
        {
            throw new JCartException(INVALID_EMAILADDRESS);
        }
        if (!StringUtils.hasText(token) || !token.equals(user.getPasswordResetToken()))
        {
            throw new JCartException("Invalid password reset token");
        }
        user.setPassword(encodedPwd);
        user.setPasswordResetToken(null);

    }

    @Override
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user)
    {
        User userByEmail = findUserByEmail(user.getEmail());
        if (nonNull(userByEmail))
        {
            throw new JCartException("Email " + user.getEmail() + " already in use");
        }
        List<Role> persistedRoles = user.getRoles().stream()
                .filter(role -> nonNull(role.getId()))
                .map(role -> roleRepository.findOne(role.getId())).collect(toList());
        user.setRoles(persistedRoles);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Integer id)
    {
        return userRepository.findOne(id);
    }

    @Override
    public User updateUser(User user)
    {
        User persistedUser = getUserById(user.getId());
        if (isNull(persistedUser))
        {
            throw new JCartException("User " + user.getId() + " doesn't exist");
        }

        List<Role> updatedRoles = user.getRoles().stream()
                .filter(role -> nonNull(role.getId()))
                .map(role -> roleRepository.findOne(role.getId())).collect(toList());
        persistedUser.setRoles(updatedRoles);
        return userRepository.save(persistedUser);
    }

    @Override
    public Role getRoleByName(String roleName)
    {
        return roleRepository.findByName(roleName);
    }
}
