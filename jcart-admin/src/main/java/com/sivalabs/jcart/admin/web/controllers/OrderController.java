/**
 * 
 */
package com.sivalabs.jcart.admin.web.controllers;

import java.util.List;

import com.sivalabs.jcart.admin.web.security.SecurityUtil;
import com.sivalabs.jcart.orders.OrderService;
import com.sivalabs.jcart.common.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sivalabs.jcart.JCartException;
import com.sivalabs.jcart.entities.Order;

/**
 * @author Siva
 *
 */
@Controller
@Secured(SecurityUtil.MANAGE_ORDERS)
@Slf4j
public class OrderController extends JCartAdminBaseController
{
	private static final String viewPrefix = "orders/";

	private OrderService orderService;
	private EmailService emailService;
	private TemplateEngine templateEngine;

	@Autowired
	public OrderController(OrderService orderService, EmailService emailService, TemplateEngine templateEngine) {
		this.orderService = orderService;
		this.emailService = emailService;
		this.templateEngine = templateEngine;
	}

	@Value("${support.email}")
	private String supportEmail;

	@Override
	protected String getHeaderTitle()
	{
		return "Manage Orders";
	}
	
	
	@RequestMapping(value="/orders", method=RequestMethod.GET)
	public String listOrders(Model model) {
		List<Order> list = orderService.getAllOrders();
		model.addAttribute("orders",list);
		return viewPrefix+"orders";
	}
	
	@RequestMapping(value="/orders/{orderNumber}", method=RequestMethod.GET)
	public String editOrderForm(@PathVariable String orderNumber, Model model) {
		Order order = orderService.getOrder(orderNumber);
		model.addAttribute("order",order);
		return viewPrefix+"edit_order";
	}
	
	@RequestMapping(value="/orders/{orderNumber}", method=RequestMethod.POST)
	public String updateOrder(@ModelAttribute("order") Order order, BindingResult result, 
			Model model, RedirectAttributes redirectAttributes) {		
		Order persistedOrder = orderService.updateOrder(order);
		this.sendOrderStatusUpdateEmail(persistedOrder);
		log.debug("Updated order with orderNumber : {}", persistedOrder.getOrderNumber());
		redirectAttributes.addFlashAttribute("info", "Order updated successfully");
		return "redirect:/orders";
	}
	
	protected void sendOrderStatusUpdateEmail(Order order)
	{
		try {
			
			// Prepare the evaluation context
	        final Context ctx = new Context();
	        ctx.setVariable("order", order);

	        // Create the HTML body using Thymeleaf
	        final String htmlContent = this.templateEngine.process("email-templates/order-status-update-email", ctx);
	        
			emailService.sendEmail(supportEmail, order.getCustomer().getEmail(),
								   "QuilCartCart - Order Status Update", 
								   htmlContent);
		} catch (JCartException e) {
			log.error(e.getMessage(), e);
		}
	}
}
