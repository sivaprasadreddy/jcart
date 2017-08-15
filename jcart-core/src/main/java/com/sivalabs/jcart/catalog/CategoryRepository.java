package com.sivalabs.jcart.catalog;

import com.sivalabs.jcart.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siva
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Category getByName(String name);
}
