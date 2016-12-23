/**
 * 
 */
package com.sivalabs.jcart.admin.web.models;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.sivalabs.jcart.entities.Category;
import com.sivalabs.jcart.entities.Product;

import lombok.Data;

/**
 * @author Siva
 *
 */
@Data
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
    private BigDecimal price = new BigDecimal("0.0");
    private String imageUrl;
    private MultipartFile image;
    private boolean disabled;
    @NotNull
    private Integer categoryId;

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
        ProductForm p = new ProductForm();
        p.setId(product.getId());
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setDisabled(product.isDisabled());
        p.setPrice(product.getPrice());
        p.setSku(product.getSku());
        p.setCategoryId(product.getCategory().getId());
        return p;
    }
}
