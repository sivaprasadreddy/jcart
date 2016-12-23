package com.sivalabs.jcart.site.web.validators;

import static java.util.Objects.nonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sivalabs.jcart.customers.CustomerService;
import com.sivalabs.jcart.entities.Customer;

/**
 * @author Siva
 *
 */
@Component
public class CustomerValidator implements Validator
{
    private CustomerService customerService;

    /**
     * Spring {@link Autowired} Constructor Injection
     * 
     * @param custmoerService
     */
    public CustomerValidator(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @Override
    public boolean supports(Class<?> clazz)
    {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        Customer customer = (Customer) target;
        Customer customerByEmail = customerService
                .getCustomerByEmail(customer.getEmail());
        if (nonNull(customerByEmail))
        {
            errors.rejectValue("email", "error.exists",
                    new Object[] { customer.getEmail() },
                    "Email " + customer.getEmail() + " already in use");
        }
    }

}
