package com.sivalabs.jcart.admin.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sivalabs.jcart.admin.security.SecurityUtil;
import com.sivalabs.jcart.customers.CustomerService;
import com.sivalabs.jcart.entities.Customer;

/**
 * @author Siva
 *
 */
@Controller
@Secured(SecurityUtil.MANAGE_CUSTOMERS)
public class CustomerController extends AbstractJCartAdminController
{
    private static final String VIEWPREFIX = "customers/";

    @Autowired
    private CustomerService customerService;

    @Override
    protected String getHeaderTitle()
    {
        return "Manage Customers";
    }

    @GetMapping(value = "/customers")
    public String listCustomers(Model model)
    {
        List<Customer> list = customerService.getAllCustomers();
        model.addAttribute("customers", list);
        return VIEWPREFIX + "customers";
    }

    @GetMapping(value = "/customers/{id}")
    public String viewCustomerForm(@PathVariable Integer id, Model model)
    {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("customer", customer);
        return VIEWPREFIX + "view_customer";
    }

}
