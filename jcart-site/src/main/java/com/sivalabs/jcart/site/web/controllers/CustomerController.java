package com.sivalabs.jcart.site.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sivalabs.jcart.customers.CustomerService;
import com.sivalabs.jcart.entities.Customer;
import com.sivalabs.jcart.entities.Order;
import com.sivalabs.jcart.site.web.validators.CustomerValidator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Siva
 *
 */

@Slf4j
@Controller
public class CustomerController extends AbstractJCartSiteController
{
    private CustomerService customerService;
    private CustomerValidator customerValidator;
    private PasswordEncoder passwordEncoder;

    /**
     * Spring {@link Autowired} Constructor Injection
     * 
     * @param customerService
     * @param customerValidator
     * @param passwordEncoder
     */
    public CustomerController(CustomerService customerService,
            CustomerValidator customerValidator, PasswordEncoder passwordEncoder)
    {
        this.customerService = customerService;
        this.customerValidator = customerValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected String getHeaderTitle()
    {
        return "Login/Register";
    }

    @GetMapping(value = "/register")
    public String registerForm(Model model)
    {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@Valid @ModelAttribute("customer") Customer customer,
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
        log.debug("Created new Customer with id : {} and email : {}",
                persistedCustomer.getId(), persistedCustomer.getEmail());
        redirectAttributes.addFlashAttribute("info", "Customer created successfully");
        return "redirect:/login";
    }

    @GetMapping(value = "/myAccount")
    public String myAccount(Model model)
    {
        String email = getCurrentUser().getCustomer().getEmail();
        Customer customer = customerService.getCustomerByEmail(email);
        model.addAttribute("customer", customer);
        List<Order> orders = customerService.getCustomerOrders(email);
        model.addAttribute("orders", orders);
        return "myAccount";
    }
}
