/**
 * 
 */
package com.sivalabs.jcart.site.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Siva
 *
 */
@Controller
public class ErrorController extends JCartSiteBaseController
{
	private static final String viewPrefix = "error/";
	
	@Override
	public String getHeaderTitle()
	{
		return "Error";
	}
	
	@RequestMapping("/403")
	public String accessDenied()
	{
		return viewPrefix+"accessDenied";
	}
	
	/*@RequestMapping("/error")
	public String error()
	{
		return viewPrefix+"error";
	}*/
}
