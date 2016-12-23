/**
 * 
 */
package com.sivalabs.jcart.site.web.models;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Siva
 *
 */
public class OrderDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "FirstName is required")
    private String firstName;
    @NotEmpty(message = "LastName is required")
    private String lastName;
    @NotEmpty(message = "EmailId is required")
    @Email
    private String emailId;
    @NotEmpty(message = "Phone is required")
    private String phone;

    @NotEmpty(message = "Address Line1 is required")
    private String addressLine1;
    private String addressLine2;
    @NotEmpty(message = "City is required")
    private String city;
    @NotEmpty(message = "State is required")
    private String state;
    @NotEmpty(message = "ZipCode is required")
    private String zipCode;
    @NotEmpty(message = "Country is required")
    private String country;

    @NotEmpty(message = "FirstName is required")
    private String billingFirstName;
    @NotEmpty(message = "LastName is required")
    private String billingLastName;
    @NotEmpty(message = "Address Line1 is required")
    private String billingAddressLine1;
    private String billingAddressLine2;
    @NotEmpty(message = "City is required")
    private String billingCity;
    @NotEmpty(message = "State is required")
    private String billingState;
    @NotEmpty(message = "ZipCode is required")
    private String billingZipCode;
    @NotEmpty(message = "Country is required")
    private String billingCountry;

    @NotEmpty(message = "Credit Card Number is required")
    private String ccNumber;
    @NotEmpty(message = "CVV is required")
    private String cvv;

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmailId()
    {
        return emailId;
    }

    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAddressLine1()
    {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1)
    {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2()
    {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2)
    {
        this.addressLine2 = addressLine2;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCcNumber()
    {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber)
    {
        this.ccNumber = ccNumber;
    }

    public String getCvv()
    {
        return cvv;
    }

    public void setCvv(String cvv)
    {
        this.cvv = cvv;
    }

    public String getBillingAddressLine1()
    {
        return billingAddressLine1;
    }

    public void setBillingAddressLine1(String billingAddressLine1)
    {
        this.billingAddressLine1 = billingAddressLine1;
    }

    public String getBillingAddressLine2()
    {
        return billingAddressLine2;
    }

    public void setBillingAddressLine2(String billingAddressLine2)
    {
        this.billingAddressLine2 = billingAddressLine2;
    }

    public String getBillingCity()
    {
        return billingCity;
    }

    public void setBillingCity(String billingCity)
    {
        this.billingCity = billingCity;
    }

    public String getBillingState()
    {
        return billingState;
    }

    public void setBillingState(String billingState)
    {
        this.billingState = billingState;
    }

    public String getBillingZipCode()
    {
        return billingZipCode;
    }

    public void setBillingZipCode(String billingZipCode)
    {
        this.billingZipCode = billingZipCode;
    }

    public String getBillingCountry()
    {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry)
    {
        this.billingCountry = billingCountry;
    }

    public String getBillingFirstName()
    {
        return billingFirstName;
    }

    public void setBillingFirstName(String billingFirstName)
    {
        this.billingFirstName = billingFirstName;
    }

    public String getBillingLastName()
    {
        return billingLastName;
    }

    public void setBillingLastName(String billingLastName)
    {
        this.billingLastName = billingLastName;
    }

}
