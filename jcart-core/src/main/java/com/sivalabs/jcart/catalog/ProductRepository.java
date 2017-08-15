package com.sivalabs.jcart.catalog;

import com.sivalabs.jcart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Siva
 *
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Product findBySku(String sku);

	@Query("select p from Product p where p.name like ?1 or p.sku like ?1 or p.description like ?1")
	List<Product> search(String query);

}
