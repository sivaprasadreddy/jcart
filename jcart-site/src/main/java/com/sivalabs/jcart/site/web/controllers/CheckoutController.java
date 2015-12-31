/**
 * 
 */
package com.sivalabs.jcart.site.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sivalabs.jcart.site.web.models.Cart;
import com.sivalabs.jcart.site.web.models.OrderDTO;

/**
 * @author Siva
 *
 */
@Controller
public class CheckoutController extends JCartSiteBaseController
{

	@Override
	protected String getHeaderTitle()
	{
		return "Checkout";
	}

	@RequestMapping(value="/checkout", method=RequestMethod.GET)
	public String checkout(HttpServletRequest request, Model model)
	{
		OrderDTO order = new OrderDTO();
		model.addAttribute("order", order);
		Cart cart = getOrCreateCart(request);
		model.addAttribute("cart", cart);
		return "checkout";
	}
}
