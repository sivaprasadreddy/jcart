/**
 * 
 */
package com.sivalabs.jcart.admin.web.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
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
import com.sivalabs.jcart.admin.web.validators.ProductFormValidator;
import com.sivalabs.jcart.catalog.CatalogService;
import com.sivalabs.jcart.entities.Category;
import com.sivalabs.jcart.entities.Product;

/**
 * @author rajakolli
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest
{
    @Autowired
    private WebApplicationContext context;

    MockMvc mockMvc;

    @MockBean
    CatalogService catalogService;
    @MockBean
    ProductFormValidator productFormValidator;

    ProductController productController;

    private String name = "Product Name";

    private String description = "Product Description";

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity())
                .build();

        Category category = new Category();
        category.setDisplayOrder(1);
        List<Category> categoryList = Arrays.asList(category);
        when(catalogService.getAllCategories()).thenReturn(categoryList);
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setDisabled(false);
        List<Product> productList = Arrays.asList(product);
        when(catalogService.getAllProducts()).thenReturn(productList);
        productController = new ProductController(catalogService, productFormValidator);
    }

    @Test
    public void testGetHeaderTitle()
    {
        String headerTitle = productController.getHeaderTitle();
        assertNotNull(headerTitle);
        assertEquals(HeaderTitleConstants.PRODUCTTITLE, headerTitle);
    }
    /**
     * Test method for
     * {@link com.sivalabs.jcart.admin.web.controllers.ProductController#categoriesList()}.
     * @throws Exception
     */
    @Test
    @WithAnonymousUser
    public void testListProductsWithOutSecurity() throws Exception
    {
        this.mockMvc.perform(get("/products")).andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "USER", "MANAGE_PRODUCTS" })
    public void testListProducts() throws Exception
    {
        this.mockMvc.perform(get("/products")).andExpect(status().isOk())
                .andExpect(content().string(containsString(name)));
    }
}
