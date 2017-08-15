/**
 * 
 */
package com.sivalabs.jcart.admin.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.sivalabs.jcart.admin.web.security.SecurityUtil;
import com.sivalabs.jcart.security.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sivalabs.jcart.admin.web.validators.UserValidator;
import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.entities.User;

/**
 * @author Siva
 *
 */
@Controller
@Secured(SecurityUtil.MANAGE_USERS)
@Slf4j
public class UserController extends JCartAdminBaseController
{
	private static final String viewPrefix = "users/";
	private SecurityService securityService;
	private UserValidator userValidator;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserController(SecurityService securityService, UserValidator userValidator, PasswordEncoder passwordEncoder) {
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
	public List<Role> rolesList(){
		return securityService.getAllRoles();
	}
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public String listUsers(Model model) {
		List<User> list = securityService.getAllUsers();
		model.addAttribute("users",list);
		return viewPrefix+"users";
	}
	
	@RequestMapping(value="/users/new", method=RequestMethod.GET)
	public String createUserForm(Model model) {
		User user = new User();
		model.addAttribute("user",user);
		//model.addAttribute("rolesList",securityService.getAllRoles());		
		
		return viewPrefix+"create_user";
	}

	@RequestMapping(value="/users", method=RequestMethod.POST)
	public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		userValidator.validate(user, result);
		if(result.hasErrors()){
			return viewPrefix+"create_user";
		}
		String password = user.getPassword();
		String encodedPwd = passwordEncoder.encode(password);
		user.setPassword(encodedPwd);
		User persistedUser = securityService.createUser(user);
		log.debug("Created new User with id : {} and name : {}", persistedUser.getId(), persistedUser.getName());
		redirectAttributes.addFlashAttribute("info", "User created successfully");
		return "redirect:/users";
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.GET)
	public String editUserForm(@PathVariable Integer id, Model model) {
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
			if(assignedRoleMap.containsKey(role.getId())){
				userRoles.add(role);
			} else {
				userRoles.add(null);
			}
		}
		user.setRoles(userRoles);
		model.addAttribute("user",user);
		//model.addAttribute("rolesList",allRoles);		
		return viewPrefix+"edit_user";
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.POST)
	public String updateUser(@ModelAttribute("user") User user, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()){
			return viewPrefix+"edit_user";
		}
		User persistedUser = securityService.updateUser(user);
		log.debug("Updated user with id : {} and name : {}", persistedUser.getId(), persistedUser.getName());
		redirectAttributes.addFlashAttribute("info", "User updates successfully");
		return "redirect:/users";
	}

}
