package com.sivalabs.jcart.customers;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.jcart.entities.Customer;
import com.sivalabs.jcart.entities.Order;

import lombok.RequiredArgsConstructor;

/**
 * @author rajakolli
 *
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService
{

    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomerByEmail(String email)
    {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Customer createCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Integer id)
    {
        return customerRepository.findOne(id);
    }

    @Override
    public List<Order> getCustomerOrders(String email)
    {
        return customerRepository.getCustomerOrders(email);
    }

}
