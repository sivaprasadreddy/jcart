package com.sivalabs.jcart.admin.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sivalabs.jcart.admin.security.SecurityUtil;
import com.sivalabs.jcart.admin.web.validators.UserValidator;
import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.entities.User;
import com.sivalabs.jcart.security.SecurityService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Siva
 *
 */
@Slf4j
@Controller
@Secured(SecurityUtil.MANAGE_USERS)
public class UserController extends AbstractJCartAdminController
{
    private static final String VIEWPREFIX = "users/";
    
    private SecurityService securityService;
    private UserValidator userValidator;
    private PasswordEncoder passwordEncoder;

    /**
     * Spring {@link Autowired}
     * 
     * @param securityService
     * @param userValidator
     * @param passwordEncoder
     */
    public UserController(SecurityService securityService, UserValidator userValidator,
            PasswordEncoder passwordEncoder)
    {
        super();
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected String getHeaderTitle()
    {
        return "Manage Users";
    }

    @ModelAttribute("rolesList")
    public List<Role> rolesList()
    {
        return securityService.getAllRoles();
    }

    @GetMapping(value = "/users")
    public String listUsers(Model model)
    {
        List<User> list = securityService.getAllUsers();
        model.addAttribute("users", list);
        return VIEWPREFIX + "users";
    }

    @GetMapping(value = "/users/new")
    public String createUserForm(Model model)
    {
        User user = new User();
        model.addAttribute("user", user);
        return VIEWPREFIX + "create_user";
    }

    @PostMapping(value = "/users")
    public String createUser(@Valid @ModelAttribute("user") User user,
            BindingResult result, Model model, RedirectAttributes redirectAttributes)
    {
        userValidator.validate(user, result);
        if (result.hasErrors())
        {
            return VIEWPREFIX + "create_user";
        }
        String password = user.getPassword();
        String encodedPwd = passwordEncoder.encode(password);
        user.setPassword(encodedPwd);
        User persistedUser = securityService.createUser(user);
        log.debug("Created new User with id : {} and name : {}", persistedUser.getId(),
                persistedUser.getName());
        redirectAttributes.addFlashAttribute("info", "User created successfully");
        return "redirect:/users";
    }

    @GetMapping(value = "/users/{id}")
    public String editUserForm(@PathVariable Integer id, Model model)
    {
        User user = securityService.getUserById(id);
        Map<Integer, Role> assignedRoleMap = new HashMap<>();
        List<Role> roles = user.getRoles();
        for (Role role : roles)
        {
            assignedRoleMap.put(role.getId(), role);
        }

        List<Role> userRoles = new ArrayList<>();
        List<Role> allRoles = securityService.getAllRoles();
        for (Role role : allRoles)
        {
            if (assignedRoleMap.containsKey(role.getId()))
            {
                userRoles.add(role);
            }
            else
            {
                userRoles.add(null);
            }
        }
        user.setRoles(userRoles);

        model.addAttribute("user", user);
        return VIEWPREFIX + "edit_user";
    }

    @PostMapping(value = "/users/{id}")
    public String updateUser(@ModelAttribute("user") User user, BindingResult result,
            Model model, RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors())
        {
            return VIEWPREFIX + "edit_user";
        }
        User persistedUser = securityService.updateUser(user);
        log.debug("Updated user with id : {} and name : {}", persistedUser.getId(),
                persistedUser.getName());
        redirectAttributes.addFlashAttribute("info", "User updates successfully");
        return "redirect:/users";
    }

    @GetMapping(value = "/myAccount")
    public String myAccount(Model model)
    {
        Integer userId = getCurrentUser().getUser().getId();
        User user = securityService.getUserById(userId);
        model.addAttribute("user", user);
        return "myAccount";
    }
}
