package com.sivalabs.jcart.site.web.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sivalabs.jcart.JCartException;
import com.sivalabs.jcart.common.services.EmailService;
import com.sivalabs.jcart.customers.CustomerService;
import com.sivalabs.jcart.entities.Address;
import com.sivalabs.jcart.entities.Customer;
import com.sivalabs.jcart.entities.Order;
import com.sivalabs.jcart.entities.OrderItem;
import com.sivalabs.jcart.entities.Payment;
import com.sivalabs.jcart.orders.OrderService;
import com.sivalabs.jcart.site.web.models.Cart;
import com.sivalabs.jcart.site.web.models.LineItem;
import com.sivalabs.jcart.site.web.models.OrderDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Siva
 *
 */
@Slf4j
@Controller
public class OrderController extends AbstractJCartSiteController
{

    private CustomerService customerService;
    private OrderService orderService;
    private EmailService emailService;

    /**
     * Spring {@link Autowired} Constructor Injection
     * 
     * @param customerService
     * @param orderService
     * @param emailService
     */
    public OrderController(CustomerService customerService, OrderService orderService,
            EmailService emailService)
    {
        this.customerService = customerService;
        this.orderService = orderService;
        this.emailService = emailService;
    }

    @Override
    protected String getHeaderTitle()
    {
        return "Order";
    }

    @PostMapping(value = "/orders")
    public String placeOrder(@Valid @ModelAttribute("order") OrderDTO order,
            BindingResult result, Model model, HttpServletRequest request)
    {
        Cart cart = getOrCreateCart(request);
        if (result.hasErrors())
        {
            model.addAttribute("cart", cart);
            return "checkout";
        }

        Order newOrder = new Order();

        String email = getCurrentUser().getCustomer().getEmail();
        Customer customer = customerService.getCustomerByEmail(email);
        newOrder.setCustomer(customer);
        Address address = new Address();
        address.setAddressLine1(order.getAddressLine1());
        address.setAddressLine2(order.getAddressLine2());
        address.setCity(order.getCity());
        address.setState(order.getState());
        address.setZipCode(order.getZipCode());
        address.setCountry(order.getCountry());

        newOrder.setDeliveryAddress(address);

        Address billingAddress = new Address();
        billingAddress.setAddressLine1(order.getAddressLine1());
        billingAddress.setAddressLine2(order.getAddressLine2());
        billingAddress.setCity(order.getCity());
        billingAddress.setState(order.getState());
        billingAddress.setZipCode(order.getZipCode());
        billingAddress.setCountry(order.getCountry());

        newOrder.setBillingAddress(billingAddress);

        Set<OrderItem> orderItems = new HashSet<>();
        List<LineItem> lineItems = cart.getItems();
        for (LineItem lineItem : lineItems)
        {
            OrderItem item = new OrderItem();
            item.setProduct(lineItem.getProduct());
            item.setQuantity(lineItem.getQuantity());
            item.setPrice(lineItem.getProduct().getPrice());
            item.setOrder(newOrder);
            orderItems.add(item);
        }

        newOrder.setItems(orderItems);

        Payment payment = new Payment();
        payment.setCcNumber(order.getCcNumber());
        payment.setCvv(order.getCvv());

        newOrder.setPayment(payment);
        Order savedOrder = orderService.createOrder(newOrder);

        this.sendOrderConfirmationEmail(savedOrder);

        request.getSession().removeAttribute("CART_KEY");
        return "redirect:orderconfirmation?orderNumber=" + savedOrder.getOrderNumber();
    }

    protected void sendOrderConfirmationEmail(Order order)
    {
        try
        {
            emailService.sendEmail(order.getCustomer().getEmail(),
                    "QuilCartCart - Order Confirmation",
                    "Your order has been placed successfully.\n" + "Order Number : "
                            + order.getOrderNumber());
        }
        catch (JCartException e)
        {
            log.error("Exception occured while attempting to send confirmation email :{}",
                    e.getMessage(), e);
        }
    }

    @GetMapping(value = "/orderconfirmation")
    public String showOrderConfirmation(
            @RequestParam(value = "orderNumber") String orderNumber, Model model)
    {
        Order order = orderService.getOrder(orderNumber);
        model.addAttribute("order", order);
        return "orderconfirmation";
    }

    @GetMapping(value = "/orders/{orderNumber}")
    public String viewOrder(@PathVariable(value = "orderNumber") String orderNumber,
            Model model)
    {
        Order order = orderService.getOrder(orderNumber);
        model.addAttribute("order", order);
        return "view_order";
    }
}
