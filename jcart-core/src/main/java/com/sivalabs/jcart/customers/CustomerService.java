/**
 * 
 */
package com.sivalabs.jcart.customers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.jcart.entities.Customer;

/**
 * @author Siva
 *
 */
@Service
@Transactional
public class CustomerService {
	@Autowired CustomerRepository customerRepository;
	
	public Customer getCustomerByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

}
