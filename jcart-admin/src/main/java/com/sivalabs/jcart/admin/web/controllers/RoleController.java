/**
 * 
 */
package com.sivalabs.jcart.admin.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sivalabs.jcart.admin.security.SecurityUtil;
import com.sivalabs.jcart.admin.web.validators.RoleValidator;
import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.security.SecurityService;

/**
 * @author Siva
 *
 */
@Controller
@Secured(SecurityUtil.MANAGE_ROLES)
public class RoleController extends JCartAdminAbstractController
{
    private static final String viewPrefix = "roles/";

    @Autowired
    protected SecurityService securityService;
    @Autowired
    private RoleValidator roleValidator;

    @Override
    protected String getHeaderTitle()
    {
        return "Manage Roles";
    }

    @ModelAttribute("permissionsList")
    public List<Permission> permissionsList()
    {
        return securityService.getAllPermissions();
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String listRoles(Model model)
    {
        List<Role> list = securityService.getAllRoles();
        model.addAttribute("roles", list);
        return viewPrefix + "roles";
    }

    @RequestMapping(value = "/roles/new", method = RequestMethod.GET)
    public String createRoleForm(Model model)
    {
        Role role = new Role();
        model.addAttribute("role", role);
        // model.addAttribute("permissionsList",securityService.getAllPermissions());

        return viewPrefix + "create_role";
    }

    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public String createRole(@Valid @ModelAttribute("role") Role role,
            BindingResult result, Model model, RedirectAttributes redirectAttributes)
    {
        roleValidator.validate(role, result);
        if (result.hasErrors())
        {
            return viewPrefix + "create_role";
        }
        Role persistedRole = securityService.createRole(role);
        logger.debug("Created new role with id : {} and name : {}", persistedRole.getId(),
                persistedRole.getName());
        redirectAttributes.addFlashAttribute("info", "Role created successfully");
        return "redirect:/roles";
    }

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public String editRoleForm(@PathVariable Integer id, Model model)
    {
        Role role = securityService.getRoleById(id);
        Map<Integer, Permission> assignedPermissionMap = new HashMap<>();
        List<Permission> permissions = role.getPermissions();
        for (Permission permission : permissions)
        {
            assignedPermissionMap.put(permission.getId(), permission);
        }
        List<Permission> rolePermissions = new ArrayList<>();
        List<Permission> allPermissions = securityService.getAllPermissions();
        for (Permission permission : allPermissions)
        {
            if (assignedPermissionMap.containsKey(permission.getId()))
            {
                rolePermissions.add(permission);
            }
            else
            {
                rolePermissions.add(null);
            }
        }
        role.setPermissions(rolePermissions);
        model.addAttribute("role", role);
        // model.addAttribute("permissionsList",allPermissions);
        return viewPrefix + "edit_role";
    }

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.POST)
    public String updateRole(@ModelAttribute("role") Role role, BindingResult result,
            Model model, RedirectAttributes redirectAttributes)
    {
        Role persistedRole = securityService.updateRole(role);
        logger.debug("Updated role with id : {} and name : {}", persistedRole.getId(),
                persistedRole.getName());
        redirectAttributes.addFlashAttribute("info", "Role updated successfully");
        return "redirect:/roles";
    }

}
