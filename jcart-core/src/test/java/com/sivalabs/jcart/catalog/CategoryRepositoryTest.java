/**
 * 
 */
package com.sivalabs.jcart.catalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.sivalabs.jcart.entities.Category;

/**
 * @author rajakolli
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest
{

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    CategoryRepository categoryRepository;

    private String description = "JUNIT Description";
    private String name = "JUNIT";

    /**
     * Test method for
     * {@link com.sivalabs.jcart.catalog.CategoryRepository#getByName(java.lang.String)}.
     */
    @Test
    public void testGetByName()
    {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        testEntityManager.persist(category);

        Category persistedCategory = categoryRepository.getByName(name);
        assertNotNull(persistedCategory);
        assertNotNull(persistedCategory.getId());
        assertEquals(description, persistedCategory.getDescription());
    }

}
