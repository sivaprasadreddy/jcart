/**
 * 
 */
package com.sivalabs.jcart.admin.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Siva
 *
 */
public class WebUtils
{
	private WebUtils()
	{
	}
	public static final String IMAGES_PREFIX = "/products/images/";
	public static final String IMAGES_DIR = "/Users/rsiva/jcart/products/";
	
	public static String getURLWithContextPath(HttpServletRequest request)
	{
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}
	
	
}
