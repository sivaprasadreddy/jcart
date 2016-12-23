/**
 * 
 */
package com.sivalabs.jcart.site.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sivalabs.jcart.customers.CustomerService;
import com.sivalabs.jcart.entities.Customer;
import com.sivalabs.jcart.entities.Order;
import com.sivalabs.jcart.site.web.validators.CustomerValidator;

/**
 * @author Siva
 *
 */
@Controller
public class CustomerController extends JCartSiteBaseAbstractController
{
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerValidator customerValidator;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Override
    protected String getHeaderTitle()
    {
        return "Login/Register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    protected String registerForm(Model model)
    {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    protected String register(@Valid @ModelAttribute("customer") Customer customer,
            BindingResult result, Model model, RedirectAttributes redirectAttributes)
    {
        customerValidator.validate(customer, result);
        if (result.hasErrors())
        {
            return "register";
        }
        String password = customer.getPassword();
        String encodedPwd = passwordEncoder.encode(password);
        customer.setPassword(encodedPwd);

        Customer persistedCustomer = customerService.createCustomer(customer);
        logger.debug("Created new Customer with id : {} and email : {}",
                persistedCustomer.getId(), persistedCustomer.getEmail());
        redirectAttributes.addFlashAttribute("info", "Customer created successfully");
        return "redirect:/login";
    }

    @RequestMapping(value = "/myAccount", method = RequestMethod.GET)
    protected String myAccount(Model model)
    {
        String email = getCurrentUser().getCustomer().getEmail();
        Customer customer = customerService.getCustomerByEmail(email);
        model.addAttribute("customer", customer);
        List<Order> orders = customerService.getCustomerOrders(email);
        model.addAttribute("orders", orders);
        return "myAccount";
    }
}
