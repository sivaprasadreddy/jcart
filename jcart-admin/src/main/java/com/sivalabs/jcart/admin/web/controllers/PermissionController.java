package com.sivalabs.jcart.admin.web.controllers;

import static com.sivalabs.jcart.admin.security.SecurityUtil.MANAGE_PERMISSIONS;
import static com.sivalabs.jcart.admin.web.utils.HeaderTitleConstants.PERMISSIONTITLE;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.security.SecurityService;

import lombok.RequiredArgsConstructor;

/**
 * @author Siva
 * @author rajakolli
 *
 */
@Controller
@Secured(MANAGE_PERMISSIONS)
@RequiredArgsConstructor
public class PermissionController extends AbstractJCartAdminController
{
    private static final String VIEWPREFIX = "permissions/";

    private final SecurityService securityService;

    @Override
    protected String getHeaderTitle()
    {
        return PERMISSIONTITLE;
    }

    @GetMapping(value = "/permissions")
    public String listPermissions(Model model)
    {
        List<Permission> list = securityService.getAllPermissions();
        model.addAttribute("permissions", list);
        return VIEWPREFIX + "permissions";
    }

}
