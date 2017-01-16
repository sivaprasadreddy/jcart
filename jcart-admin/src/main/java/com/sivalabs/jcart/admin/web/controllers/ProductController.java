/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sivalabs.jcart.admin.web.controllers;

import static com.sivalabs.jcart.admin.security.SecurityUtil.MANAGE_PRODUCTS;
import static com.sivalabs.jcart.admin.web.utils.HeaderTitleConstants.PRODUCTTITLE;
import static com.sivalabs.jcart.admin.web.utils.WebUtils.IMAGES_DIR;
import static java.util.Objects.nonNull;
import static org.apache.commons.io.IOUtils.copy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sivalabs.jcart.JCartException;
import com.sivalabs.jcart.admin.web.models.ProductForm;
import com.sivalabs.jcart.admin.web.validators.ProductFormValidator;
import com.sivalabs.jcart.catalog.CatalogService;
import com.sivalabs.jcart.entities.Category;
import com.sivalabs.jcart.entities.Product;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Siva
 * @author rajakolli
 *
 */
@Slf4j
@Controller
@Secured(MANAGE_PRODUCTS)
public class ProductController extends AbstractJCartAdminController
{

    private static final String VIEWPREFIX = "products/";

    private CatalogService catalogService;
    private ProductFormValidator productFormValidator;

    /**
     * Spring {@link Autowired} Constructor Injection
     * 
     * @param catalogService
     * @param productFormValidator
     */
    public ProductController(CatalogService catalogService,
            ProductFormValidator productFormValidator)
    {
        this.catalogService = catalogService;
        this.productFormValidator = productFormValidator;
    }

    @Override
    protected String getHeaderTitle()
    {
        return PRODUCTTITLE;
    }

    @ModelAttribute("categoriesList")
    public List<Category> categoriesList()
    {
        return catalogService.getAllCategories();
    }

    @GetMapping(value = "/products")
    public String listProducts(Model model)
    {
        model.addAttribute("products", catalogService.getAllProducts());
        return VIEWPREFIX + "products";
    }

    @GetMapping(value = "/products/new")
    public String createProductForm(Model model)
    {
        ProductForm product = new ProductForm();
        model.addAttribute("product", product);
        return VIEWPREFIX + "create_product";
    }

    @PostMapping(value = "/products")
    public String createProduct(@Valid @ModelAttribute("product") ProductForm productForm,
            BindingResult result, Model model, RedirectAttributes redirectAttributes)
    {
        productFormValidator.validate(productForm, result);
        if (result.hasErrors())
        {
            return VIEWPREFIX + "create_product";
        }
        Product product = productForm.toProduct();
        Product persistedProduct = catalogService.createProduct(product);
        productForm.setId(product.getId());
        this.saveProductImageToDisk(productForm);
        log.debug("Created new product with id : {} and name : {}",
                persistedProduct.getId(), persistedProduct.getName());
        redirectAttributes.addFlashAttribute("info", "Product created successfully");
        return "redirect:/products";
    }

    @GetMapping(value = "/products/{id}")
    public String editProductForm(@PathVariable Integer id, Model model)
    {
        Product product = catalogService.getProductById(id);
        model.addAttribute("product", ProductForm.fromProduct(product));
        return VIEWPREFIX + "edit_product";
    }

    @GetMapping(value = "/products/images/{productId}")
    public void showProductImage(@PathVariable String productId,
            HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            FileSystemResource file = new FileSystemResource(
                    IMAGES_DIR + productId + ".jpg");
            response.setContentType("image/jpg");
            copy(file.getInputStream(), response.getOutputStream());
            response.flushBuffer();
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }
    }

    @PostMapping(value = "/products/{id}")
    public String updateProduct(@Valid @ModelAttribute("product") ProductForm productForm,
            BindingResult result, Model model, RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors())
        {
            return VIEWPREFIX + "edit_product";
        }
        Product product = productForm.toProduct();
        Product persistedProduct = catalogService.updateProduct(product);
        this.saveProductImageToDisk(productForm);
        log.debug("Updated product with id : {} and name : {}", persistedProduct.getId(),
                persistedProduct.getName());
        redirectAttributes.addFlashAttribute("info", "Product updated successfully");
        return "redirect:/products";
    }

    protected void saveProductImageToDisk(ProductForm productForm)
    {
        MultipartFile file = productForm.getImage();
        if (nonNull(file) && !file.isEmpty())
        {
            String name = new StringBuilder(IMAGES_DIR).append(productForm.getId())
                    .append(".jpg").toString();
            try (BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(new File(name))))
            {
                byte[] bytes = file.getBytes();
                stream.write(bytes);
            }
            catch (IOException e)
            {
                throw new JCartException(e);
            }
        }
    }
}
