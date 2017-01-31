/**
 * 
 */
package com.sivalabs.jcart.admin.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sivalabs.jcart.admin.security.SecurityUtil;
import com.sivalabs.jcart.admin.web.validators.RoleValidator;
import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.entities.Role;
import com.sivalabs.jcart.security.SecurityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Siva
 *
 */
@Slf4j
@Controller
@Secured(SecurityUtil.MANAGE_ROLES)
@RequiredArgsConstructor
public class RoleController extends AbstractJCartAdminController
{
    private static final String VIEWPREFIX = "roles/";

    private final SecurityService securityService;
    private final RoleValidator roleValidator;
    
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

    @GetMapping(value = "/roles")
    public String listRoles(Model model)
    {
        List<Role> list = securityService.getAllRoles();
        model.addAttribute("roles", list);
        return VIEWPREFIX + "roles";
    }

    @GetMapping(value = "/roles/new")
    public String createRoleForm(Model model)
    {
        Role role = new Role();
        model.addAttribute("role", role);
        return VIEWPREFIX + "create_role";
    }

    @PostMapping(value = "/roles")
    public String createRole(@Valid @ModelAttribute("role") Role role,
            BindingResult result, Model model, RedirectAttributes redirectAttributes)
    {
        roleValidator.validate(role, result);
        if (result.hasErrors())
        {
            return VIEWPREFIX + "create_role";
        }
        Role persistedRole = securityService.createRole(role);
        log.debug("Created new role with id : {} and name : {}", persistedRole.getId(),
                persistedRole.getName());
        redirectAttributes.addFlashAttribute("info", "Role created successfully");
        return "redirect:/roles";
    }

    @GetMapping(value = "/roles/{id}")
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
        return VIEWPREFIX + "edit_role";
    }

    @PostMapping(value = "/roles/{id}")
    public String updateRole(@ModelAttribute("role") Role role, BindingResult result,
            Model model, RedirectAttributes redirectAttributes)
    {
        Role persistedRole = securityService.updateRole(role);
        log.debug("Updated role with id : {} and name : {}", persistedRole.getId(),
                persistedRole.getName());
        redirectAttributes.addFlashAttribute("info", "Role updated successfully");
        return "redirect:/roles";
    }

}
