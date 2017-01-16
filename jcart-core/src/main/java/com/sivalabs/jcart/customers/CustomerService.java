package com.sivalabs.jcart.customers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.jcart.entities.Customer;
import com.sivalabs.jcart.entities.Order;

/**
 * @author Siva
 *
 */
@Service
@Transactional
public class CustomerService
{
    private CustomerRepository customerRepository;

    /**
     * Spring {@link Autowired}
     * 
     * @param customerRepository
     */
    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerByEmail(String email)
    {
        return customerRepository.findByEmail(email);
    }

    public Customer createCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id)
    {
        return customerRepository.findOne(id);
    }

    public List<Order> getCustomerOrders(String email)
    {
        return customerRepository.getCustomerOrders(email);
    }

}
