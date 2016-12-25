/**
 * 
 */
package com.sivalabs.jcart.admin.web.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sivalabs.jcart.admin.web.models.ProductForm;
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
@WebMvcTest(controllers = { ProductController.class })
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

    Product product = new Product();

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
        product.setName(name);
        product.setSku("P101");
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

    @Test
    @WithMockUser(username = "admin", roles = { "USER", "MANAGE_PRODUCTS" })
    public void testCreateProductForm() throws Exception
    {
        this.mockMvc.perform(get("/products/new"))
                .andExpect(view().name("products/create_product"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = { "USER", "MANAGE_PRODUCTS" })
    public void testCreateProductValidationFails() throws Exception
    {
        this.mockMvc.perform(post("/products")).andExpect(status().isOk())
                .andExpect(view().name("products/create_product"))
                .andExpect(model().attributeHasFieldErrors("product",
                        new String[] { "categoryId", "sku", "name", "price" }));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "USER", "MANAGE_PRODUCTS" })
    public void testCreateProductValidationPass() throws Exception
    {
        product.setId(1);
        when(catalogService.createProduct(any())).thenReturn(product);
        this.mockMvc
                .perform(post("/products").param("categoryId", "1").param("sku", "P0101")
                        .param("name", "product").param("price", "250.00"))
                .andDo(print())
                .andExpect(flash().attribute("info",
                        equalTo("Product created successfully")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"))
                .andExpect(redirectedUrl("/products"));
    }

    @Test
    public void testSaveProductImageToDisk() throws IOException
    {
        String filePath = this.getClass().getClassLoader().getResource("quilcart.png")
                .getPath();
        FileInputStream content = new FileInputStream(filePath);
        MockMultipartFile image = new MockMultipartFile("quilcart", "quilcart.png",
                "multipart/form-data", content);
        ProductForm productForm = new ProductForm();
        productForm.setId(10);
        productForm.setImage(image);
        productController.saveProductImageToDisk(productForm);
        HttpServletResponse response = new MockHttpServletResponse();
        productController.showProductImage(String.valueOf(productForm.getId()), null,
                response);
        assertEquals("image/jpg", response.getContentType());
    }
}
