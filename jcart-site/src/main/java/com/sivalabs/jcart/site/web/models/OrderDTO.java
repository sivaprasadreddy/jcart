/**
 * 
 */
package com.sivalabs.jcart.site.web.models;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Siva
 *
 */
public class OrderDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @NotEmpty(message = "FirstName is required")
    private String firstName;
    
    @Getter
    @Setter
    @NotEmpty(message = "LastName is required")
    private String lastName;
    
    @Getter
    @Setter
    @NotEmpty(message = "EmailId is required")
    @Email
    private String emailId;
    
    @Getter
    @Setter
    @NotEmpty(message = "Phone is required")
    private String phone;
    
    @Getter
    @Setter
    @NotEmpty(message = "Address Line1 is required")
    private String addressLine1;
    
    @Getter
    @Setter
    private String addressLine2;
    
    @Getter
    @Setter
    @NotEmpty(message = "City is required")
    private String city;
    
    @Getter
    @Setter
    @NotEmpty(message = "State is required")
    private String state;
    
    @Getter
    @Setter
    @NotEmpty(message = "ZipCode is required")
    private String zipCode;
    
    @Getter
    @Setter
    @NotEmpty(message = "Country is required")
    private String country;

    @Getter
    @Setter
    @NotEmpty(message = "FirstName is required")
    private String billingFirstName;
    
    @Getter
    @Setter
    @NotEmpty(message = "LastName is required")
    private String billingLastName;
    
    @Getter
    @Setter
    @NotEmpty(message = "Address Line1 is required")
    private String billingAddressLine1;
    
    @Getter
    @Setter
    private String billingAddressLine2;
    
    @Getter
    @Setter
    @NotEmpty(message = "City is required")
    private String billingCity;
    
    @Getter
    @Setter
    @NotEmpty(message = "State is required")
    private String billingState;
    
    @Getter
    @Setter
    @NotEmpty(message = "ZipCode is required")
    private String billingZipCode;
    
    @Getter
    @Setter
    @NotEmpty(message = "Country is required")
    private String billingCountry;

    @Getter
    @Setter
    @NotEmpty(message = "Credit Card Number is required")
    private String ccNumber;
    
    @Getter
    @Setter
    @NotEmpty(message = "CVV is required")
    private String cvv;

}
