package com.sivalabs.jcart.admin.web.validators;

import static java.util.Objects.nonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sivalabs.jcart.admin.web.models.ProductForm;
import com.sivalabs.jcart.catalog.CatalogService;
import com.sivalabs.jcart.entities.Product;

/**
 * @author Siva
 *
 */
@Component
public class ProductFormValidator implements Validator
{
    @Autowired
    protected MessageSource messageSource;
    @Autowired
    protected CatalogService catalogService;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return Product.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        ProductForm productForm = (ProductForm) target;
        String sku = productForm.getSku();
        Product product = catalogService.getProductBySku(sku);
        if (nonNull(product))
        {
            errors.rejectValue("sku", "error.exists", new Object[] { sku },
                    "Product SKU " + sku + " already exists");
        }
    }

}
