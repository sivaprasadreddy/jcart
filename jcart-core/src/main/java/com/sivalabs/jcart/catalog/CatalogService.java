package com.sivalabs.jcart.catalog;

import java.util.List;

import com.sivalabs.jcart.entities.Category;
import com.sivalabs.jcart.entities.Product;

public interface CatalogService
{

    Product getProductBySku(String sku);

    List<Category> getAllCategories();

    Category getCategoryByName(String name);

    List<Product> searchProducts(String query);

    Category createCategory(Category category);

    Category getCategoryById(Integer id);

    Category updateCategory(Category category);

    List<Product> getAllProducts();

    Product createProduct(Product product);

    Product getProductById(Integer id);

    Product updateProduct(Product product);

}
