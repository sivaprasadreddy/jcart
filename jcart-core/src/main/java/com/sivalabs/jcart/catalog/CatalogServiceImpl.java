/**
 * 
 */
package com.sivalabs.jcart.catalog;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.jcart.JCartException;
import com.sivalabs.jcart.entities.Category;
import com.sivalabs.jcart.entities.Product;

import lombok.RequiredArgsConstructor;

/**
 * @author rajakolli
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService
{
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    @Override
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    @Override
    public Category getCategoryByName(String name)
    {
        return categoryRepository.getByName(name);
    }

    @Override
    public Category getCategoryById(Integer id)
    {
        return categoryRepository.findOne(id);
    }

    @Override
    public Category createCategory(Category category)
    {
        Category persistedCategory = getCategoryByName(category.getName());
        if (nonNull(persistedCategory))
        {
            throw new JCartException("Category " + category.getName() + " already exist");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category)
    {
        Category persistedCategory = getCategoryById(category.getId());
        if (isNull(persistedCategory))
        {
            throw new JCartException("Category " + category.getId() + " doesn't exist");
        }
        persistedCategory.setDescription(category.getDescription());
        persistedCategory.setDisplayOrder(category.getDisplayOrder());
        persistedCategory.setDisabled(category.isDisabled());
        return categoryRepository.save(persistedCategory);
    }

    @Override
    public Product getProductById(Integer id)
    {
        return productRepository.findOne(id);
    }

    @Override
    public Product getProductBySku(String sku)
    {
        return productRepository.findBySku(sku);
    }

    @Override
    public Product createProduct(Product product)
    {
        Product persistedProduct = getProductBySku(product.getName());
        if (nonNull(persistedProduct))
        {
            throw new JCartException(
                    "Product SKU " + product.getSku() + " already exist");
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product)
    {
        Product persistedProduct = getProductById(product.getId());
        if (isNull(persistedProduct))
        {
            throw new JCartException("Product " + product.getId() + " doesn't exist");
        }
        persistedProduct.setDescription(product.getDescription());
        persistedProduct.setDisabled(product.isDisabled());
        persistedProduct.setPrice(product.getPrice());
        persistedProduct.setCategory(getCategoryById(product.getCategory().getId()));
        return productRepository.save(persistedProduct);
    }

    @Override
    public List<Product> searchProducts(String query)
    {
        return productRepository.search("%" + query + "%");
    }
}
