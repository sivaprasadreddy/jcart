package com.sivalabs.jcart.security;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.sivalabs.jcart.JCartException;
import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.entities.User;

/**
 * @author Siva
 *
 */
@Service
@Transactional
public class SecurityService
{
    private static final String INVALID_EMAILADDRESS = "INVALID_EMAILADDRESS";
    private UserRepository userRepository;
    private PermissionRepository permissionRepository;
    private RoleRepository roleRepository;

    /**
     * Spring {@link Autowired} Constructor Injection
     * 
     * @param userRepository
     * @param permissionRepository
     * @param roleRepository
     */
    public SecurityService(UserRepository userRepository,
            PermissionRepository permissionRepository, RoleRepository roleRepository)
    {
        super();
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    public User findUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

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

    public void updatePassword(String email, String token, String password)
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
        user.setPassword(password);
        user.setPasswordResetToken(null);
    }

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

    public List<Permission> getAllPermissions()
    {
        return permissionRepository.findAll();
    }

    public List<Role> getAllRoles()
    {
        return roleRepository.findAll();
    }

    public Role getRoleByName(String roleName)
    {
        return roleRepository.findByName(roleName);
    }

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

    public Role getRoleById(Integer id)
    {
        return roleRepository.findOne(id);
    }

    public User getUserById(Integer id)
    {
        return userRepository.findOne(id);
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

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

}
