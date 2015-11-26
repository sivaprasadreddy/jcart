/**
 * 
 */
package com.sivalabs.jcart.admin.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Siva
 *
 */
@Controller
public class HomeController 
{	
	
	@RequestMapping("/home")
	public String home(Model model)
	{
		return "home";
	}
	
}
