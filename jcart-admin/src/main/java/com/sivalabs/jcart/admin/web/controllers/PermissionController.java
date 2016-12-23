package com.sivalabs.jcart.admin.web.controllers;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sivalabs.jcart.admin.security.SecurityUtil;
import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.security.SecurityService;

/**
 * @author Siva
 *
 */
@Controller
@Secured(SecurityUtil.MANAGE_PERMISSIONS)
public class PermissionController extends AbstractJCartAdminController
{
    private static final String VIEWPREFIX = "permissions/";

    private SecurityService securityService;

    /**
     * @param securityService
     */
    public PermissionController(SecurityService securityService)
    {
        super();
        this.securityService = securityService;
    }

    @Override
    protected String getHeaderTitle()
    {
        return "Manage Permissions";
    }

    @GetMapping(value = "/permissions")
    public String listPermissions(Model model)
    {
        List<Permission> list = securityService.getAllPermissions();
        model.addAttribute("permissions", list);
        return VIEWPREFIX + "permissions";
    }

}
