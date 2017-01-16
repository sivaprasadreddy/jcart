package com.sivalabs.jcart.admin.web.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sivalabs.jcart.admin.web.utils.HeaderTitleConstants;
import com.sivalabs.jcart.entities.Permission;
import com.sivalabs.jcart.security.SecurityService;

/**
 * @author rajakolli
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PermissionController.class)
public class PermissionControllerTest
{

    @Autowired
    private WebApplicationContext context;

    MockMvc mockMvc;

    @MockBean
    SecurityService securityService;

    private PermissionController permissionController;

    private String name = "JUNIT Name";

    private String description = "JUNIT Description";

    @Before
    public void setUp() throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity())
                .build();
        Permission permission = new Permission();
        permission.setName(name);
        permission.setDescription(description);
        List<Permission> permissionList = Arrays.asList(permission);
        when(securityService.getAllPermissions()).thenReturn(permissionList);
        permissionController = new PermissionController(securityService);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.admin.web.controllers.PermissionController#getHeaderTitle()}.
     */
    @Test
    public void testGetHeaderTitle()
    {
        String headerTitle = permissionController.getHeaderTitle();
        assertNotNull(headerTitle);
        assertEquals(HeaderTitleConstants.PERMISSIONTITLE, headerTitle);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.admin.web.controllers.PermissionController#listPermissions(org.springframework.ui.Model)}.
     */
    @Test
    @WithMockUser(username = "admin", roles = { "USER", "MANAGE_PERMISSIONS" })
    public void testListPermissions() throws Exception
    {
        this.mockMvc.perform(get("/permissions")).andExpect(status().isOk())
                .andExpect(content().string(containsString(name)));
    }

    @Test
    @WithAnonymousUser
    public void testListPermissionsWithAnonymousUser() throws Exception
    {
        this.mockMvc.perform(get("/permissions")).andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "MANAGE_PERMISSIONS" })
    public void testIsLoggedIn()
    {
        assertFalse(AbstractJCartAdminController.isLoggedIn());
    }

}
