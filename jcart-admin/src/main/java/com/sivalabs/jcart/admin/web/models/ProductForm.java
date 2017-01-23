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
package com.sivalabs.jcart.admin.web.models;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.sivalabs.jcart.entities.Category;
import com.sivalabs.jcart.entities.Product;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Siva
 * @author rajakolli
 *
 */
@Getter
@Setter
public class ProductForm
{
    private Integer id;

    @NotEmpty
    private String sku;

    @NotEmpty
    private String name;

    private String description;

    @NotNull
    @DecimalMin("0.1")
    private BigDecimal price;

    private String imageUrl;

    private MultipartFile image;

    private boolean disabled;

    @NotNull
    private Integer categoryId;

    public ProductForm()
    {
        super();
    }

    public Product toProduct()
    {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setDisabled(disabled);
        product.setPrice(price);
        product.setSku(sku);
        Category category = new Category();
        category.setId(categoryId);
        product.setCategory(category);
        return product;
    }

    public static ProductForm fromProduct(Product product)
    {
        ProductForm productForm = new ProductForm();
        productForm.setId(product.getId());
        productForm.setName(product.getName());
        productForm.setDescription(product.getDescription());
        productForm.setDisabled(product.isDisabled());
        productForm.setPrice(product.getPrice());
        productForm.setSku(product.getSku());
        productForm.setCategoryId(product.getCategory().getId());
        return productForm;
    }
}
