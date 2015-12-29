/**
 * 
 */
package com.sivalabs.jcart.customers;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivalabs.jcart.entities.Customer;

/**
 * @author Siva
 *
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Customer findByEmail(String email);

}
