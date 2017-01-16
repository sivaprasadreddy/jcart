/**
 * 
 */
package com.sivalabs.jcart.customers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.sivalabs.jcart.entities.Customer;
import com.sivalabs.jcart.entities.Order;

/**
 * @author rajakolli
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceTest
{

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    CustomerRepository customerRepository;

    CustomerService customerService;
    private String email = "abc@abc.com";
    private String firstName = "JUNIT FirstName";
    private String lastName = "JUNIT LastName";
    private String password = "password";

    Customer customer = new Customer();

    @Before
    public void SetUp()
    {
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPassword(password);
        testEntityManager.persist(customer);
        customerService = new CustomerService(customerRepository);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.customers.CustomerService#getCustomerByEmail(java.lang.String)}.
     */
    @Test
    public void testGetCustomerByEmail()
    {
        Customer customer = customerService.getCustomerByEmail(email);
        assertEquals(customer.getEmail(), email);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.customers.CustomerService#createCustomer(com.sivalabs.jcart.entities.Customer)}.
     */
    @Test
    public void testCreateCustomer()
    {
        Customer peristedCustomer = customerService.createCustomer(customer);
        assertEquals(peristedCustomer.getEmail(), email);
        assertEquals(peristedCustomer.getFirstName(), firstName);
        assertEquals(peristedCustomer.getPassword(), password);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.customers.CustomerService#getAllCustomers()}.
     */
    @Test
    public void testGetAllCustomers()
    {
        List<Customer> customerList = customerService.getAllCustomers();
        assertTrue(customerList.size() == 1);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.customers.CustomerService#getCustomerById(java.lang.Integer)}.
     */
    @Test
    public void testGetCustomerById()
    {
        Customer dbCustomer = customerService.getCustomerById(1);
        assertNotNull(dbCustomer);
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.customers.CustomerService#getCustomerOrders(java.lang.String)}.
     */
    @Test
    public void testGetCustomerOrders()
    {
        List<Order> dbCustomer = customerService.getCustomerOrders(email);
        assertTrue(dbCustomer.isEmpty());
    }

}
