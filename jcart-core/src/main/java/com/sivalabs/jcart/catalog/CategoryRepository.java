package com.sivalabs.jcart.catalog;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivalabs.jcart.entities.Category;

/**
 * @author Siva
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>
{

    /**
     * @param name
     * @return {@link Category}
     */
    Category getByName(String name);

}
