/**
 * 
 */
package com.sivalabs.jcart.catalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.sivalabs.jcart.entities.Product;

/**
 * @author rajakolli
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest
{

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ProductRepository productRepository;

    private String description = "JUNIT Description";
    private String name = "JUNIT";

    private String sku = "JUNIT Sku";

    @Test
    public void testFindByName()
    {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setSku(sku);
        testEntityManager.persist(product);

        product = productRepository.findByName(name);
        assertNotNull(product.getId());
        assertTrue(product.getName().equals(name));
        product = productRepository.findBySku(sku);
        assertTrue(product.getSku().equals(sku));

        List<Product> productList = productRepository.search(description);
        assertEquals(1, productList.size());

    }

}
