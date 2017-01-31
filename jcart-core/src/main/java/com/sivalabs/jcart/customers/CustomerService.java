package com.sivalabs.jcart.customers;

import java.util.List;

import com.sivalabs.jcart.entities.Customer;
import com.sivalabs.jcart.entities.Order;

/**
 * @author Siva
 *
 */
public interface CustomerService
{
    public Customer getCustomerByEmail(String email);

    public Customer createCustomer(Customer customer);

    public List<Customer> getAllCustomers();

    public Customer getCustomerById(Integer id);

    public List<Order> getCustomerOrders(String email);

}
