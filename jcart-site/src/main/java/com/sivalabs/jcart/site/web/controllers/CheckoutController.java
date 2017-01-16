package com.sivalabs.jcart.site.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sivalabs.jcart.site.web.models.Cart;
import com.sivalabs.jcart.site.web.models.OrderDTO;

/**
 * @author Siva
 *
 */
@Controller
public class CheckoutController extends AbstractJCartSiteController
{

    @Override
    protected String getHeaderTitle()
    {
        return "Checkout";
    }

    @GetMapping(value = "/checkout")
    public String checkout(HttpServletRequest request, Model model)
    {
        OrderDTO order = new OrderDTO();
        model.addAttribute("order", order);
        Cart cart = getOrCreateCart(request);
        model.addAttribute("cart", cart);
        return "checkout";
    }
}
